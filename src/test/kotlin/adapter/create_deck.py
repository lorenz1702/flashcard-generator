# create_deck.py
import sys
import genanki
import csv

# Prüfe, ob die richtigen Argumente übergeben wurden
if len(sys.argv) < 3:
    print("Error: Missing arguments. Usage: python create_deck.py <csv_path> <output_path>")
    sys.exit(1)

csv_path = sys.argv[1]
output_path = sys.argv[2]

# Standard Anki Deck und Model IDs
DECK_ID = 123456789
MODEL_ID = 987654321

my_model = genanki.Model(
    MODEL_ID,
    'Simple Model For Test',
    fields=[{'name': 'Front'}, {'name': 'Back'}, {'name': 'Audio'}],
    templates=[{
        'name': 'Card 1',
        'qfmt': '{{Front}}',
        'afmt': '{{FrontSide}}<hr id="answer">{{Back}}<br>{{Audio}}',
    }]
)

my_deck = genanki.Deck(DECK_ID, 'Test Deck from Kotlin')

try:
    with open(csv_path, 'r', encoding='utf-8') as file:
        reader = csv.reader(file, delimiter=';')
        for row in reader:
            front, back, audio_filename = row
            note = genanki.Note(model=my_model, fields=[front, back, f'[sound:{audio_filename}]'])
            my_deck.add_note(note)
except FileNotFoundError:
    print(f"Error: CSV file not found at {csv_path}")
    sys.exit(1)

# Schreibe das .apkg-Paket
genanki.Package(my_deck).write_to_file(output_path)

print(f"Successfully created deck at {output_path}")