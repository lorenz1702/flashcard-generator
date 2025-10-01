# create_deck.py
import sys
import os
import re
import genanki
import csv

if len(sys.argv) < 3:
    print("Error: Missing arguments. Usage: python create_deck.py <csv_path> <output_path>")
    sys.exit(1)

csv_path = sys.argv[1]
output_path = sys.argv[2]

DECK_ID = 123456789
MODEL_ID = 987654321

# Felder:
#  - AudioTag: enthält bereits den fertigen [sound:...] String
#  - Hint: ausklappbarer Hinweis
#  - Translation: Rückseite/Übersetzung
my_model = genanki.Model(
    MODEL_ID,
    'AudioTag (CSV) + Hint → Translation',
    fields=[
        {'name': 'AudioTag'},
        {'name': 'Hint'},
        {'name': 'Translation'},
    ],
    templates=[{
        'name': 'Card 1',
        # Front: rendert den Sound-Tag direkt aus der CSV + Hint als ausklappbar
        'qfmt': '{{AudioTag}}\n{{hint:Hint}}',
        # Back: zeigt die Übersetzung
        'afmt': '{{FrontSide}}<hr id="answer">{{Translation}}',
    }],
    css="""
.card {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, 'Apple Color Emoji','Segoe UI Emoji';
  font-size: 20px;
  text-align: center;
}
#answer { margin: 12px 0; }
"""
)

my_deck = genanki.Deck(DECK_ID, 'Test Deck from Kotlin')

# Media-Dateien sammeln (aus [sound:...]-Tags)
media_files = set()
csv_dir = os.path.dirname(os.path.abspath(csv_path))
sound_re = re.compile(r'\[sound:([^\]]+)\]', re.IGNORECASE)

def maybe_skip_header(first_row):
    norm = [c.strip().lower() for c in first_row]
    return norm == ['audiotag', 'hint', 'translation'] or norm == ['audio', 'hint', 'translation']

try:
    with open(csv_path, 'r', encoding='utf-8-sig', newline='') as file:
        reader = csv.reader(file, delimiter=';')
        rows = list(reader)

        if not rows:
            print("Error: CSV is empty.")
            sys.exit(1)

        start_idx = 1 if maybe_skip_header(rows[0]) else 0

        for i, row in enumerate(rows[start_idx:], start=start_idx+1):
            if len(row) < 3:
                print(f"Warning: Row {i} has fewer than 3 columns. Skipping. Content={row}")
                continue

            audio_tag = (row[0] or '').strip()     # z.B. "[sound:hallo.mp3]"
            hint = (row[1] or '').strip()
            translation = (row[2] or '').strip()

            if not audio_tag:
                print(f"Warning: Row {i} has empty AudioTag. Skipping.")
                continue

            # Note mit 3 Feldern anlegen
            note = genanki.Note(model=my_model, fields=[audio_tag, hint, translation])
            my_deck.add_note(note)

            # Aus dem Tag die(n) Dateinamen extrahieren und zum Media-Set hinzufügen
            for m in sound_re.finditer(audio_tag):
                fname = m.group(1).strip()
                # Nur Basename nutzen (falls jemand aus Versehen Pfad reinschreibt)
                fname = os.path.basename(fname)
                candidate = os.path.join(csv_dir, fname)
                if os.path.isfile(candidate):
                    media_files.add(candidate)
                else:
                    print(f"Warning: Media file not found: {candidate}")

except FileNotFoundError:
    print(f"Error: CSV file not found at {csv_path}")
    sys.exit(1)
except Exception as e:
    print(f"Error while reading CSV: {e}")
    sys.exit(1)

# Paket schreiben – inkl. Media
pkg = genanki.Package(my_deck)
if media_files:
    pkg.media_files = list(media_files)
else:
    print("Warning: No media files were found. The deck will have no audio.")

try:
    out_dir = os.path.dirname(os.path.abspath(output_path))
    if out_dir and not os.path.isdir(out_dir):
        os.makedirs(out_dir, exist_ok=True)

    pkg.write_to_file(output_path)
    print(f"Successfully created deck at {output_path}")
except Exception as e:
    print(f"Error while writing .apkg: {e}")
    sys.exit(1)
