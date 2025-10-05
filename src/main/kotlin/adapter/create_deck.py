import genanki
import csv
import sys
import os
import re


if len(sys.argv) < 3:
    print("Error: Missing arguments. Usage: python create_deck.py <csv_path> <output_path>")
    sys.exit(1)

csv_path = sys.argv[1]
output_path = sys.argv[2]
# --- Konfiguration ---

DECK_NAME = 'Mein Deutsch-Englisch Deck'
MODEL_NAME = 'Deutsch-Englisch mit Audio'
MEDIA_FOLDER = 'media'

# --- Eindeutige IDs für das Deck und das Notiz-Modell ---
# Du kannst diese Zahlen beliebig ändern, solange sie eindeutig sind.
DECK_ID = 1489156491
MODEL_ID = 1995182113

# --- 1. Definiere das Karten-Layout (Model) ---
# Wir brauchen drei Felder: Deutsch (für den Hinweis), Englisch und Audio.
anki_model = genanki.Model(
    MODEL_ID,
    "Audio Hint Model",
    fields=[
        {"name": "Front"},
        {"name": "Back"},
        {"name": "Audio"},
    ],
    templates=[
        # Polnisch → Deutsch (Audio zum Polnischen Text)
        {
            "name": "Card 1: Audio to Text",
            "qfmt": "{{Audio}}<br><br><button onclick=\"document.getElementById('hint').style.display='block';\">Show Hint</button><div id='hint' style='display:none;'>{{Front}}</div>",  # Audio und versteckter Hinweis
            "afmt": "{{Audio}}<br><br><b>Hint:</b> {{Front}}<br><hr>{{Back}}",  # Antwort
        },
    ],
)

# Anki-Model (angepasst für den neuen Hinweisstil)
model_id = 2607392321  # Zufällige große Zahl
anki_model_2 = genanki.Model(
    model_id,
    "Audio Hint Model",
    fields=[
        {"name": "Front"},
        {"name": "Back"},
        {"name": "Audio"},
    ],
    templates=[
        # Deutsch → Polnisch
        {
            "name": "Card 2: Text to Audio",
            "qfmt": "{{Front}}",  # Textfrage
            "afmt": "{{Front}}<br><hr>{{Back}}<br>{{Audio}}",  # Antwort mit Audio
        },
    ],
)

my_deck = genanki.Deck(DECK_ID, 'Test Deck from Kotlin')

# Media-Dateien sammeln (aus [sound:...]-Tags)
media_files = set()
csv_dir = os.path.dirname(os.path.abspath(csv_path))
sound_re = re.compile(r'\[sound:([^\]]+)\]', re.IGNORECASE)


# --- 3. Lese die CSV-Datei und füge Notizen zum Deck hinzu ---
try:
    with (open(csv_path, 'r', encoding='utf-8') as csvfile):
        reader = csv.reader(csvfile, delimiter=';') # Annahme: Semikolon als Trennzeichen
        for row in reader:
            original_text, audio_tag, translated_text = row

            # Karte für Polnisch → Deutsch (Audio mit verstecktem Hinweis)
            note_1 = genanki.Note(
                model=anki_model,
                fields=[original_text, translated_text, audio_tag]
            )
            my_deck.add_note(note_1)

            # 2. Deutsch → Polnisch (Text -> Audio)
            note_2 = genanki.Note(
                model=anki_model_2,
                fields=[translated_text, original_text, audio_tag]
            )
            my_deck.add_note(note_2)

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
    print(f"FEHLER: Die Datei '{csv_path}' wurde nicht gefunden. Stelle sicher, dass sie im selben Ordner liegt.")
    exit()

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