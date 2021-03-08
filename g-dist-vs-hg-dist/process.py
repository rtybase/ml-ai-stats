import os
import re

BOND_PATTERN = re.compile('[0-9]{2,3}[A-Z]{2}[0-9]{6}')
FOLDER_WITH_FILES = './files/'

dates = {}
series = {}
results = {}

def extract_series(bond_id):
    res = re.search('[A-Z]{2}', bond_id)
    if res:
        return res.group()
    return ''

def extract_date(filename):
    res = re.search('[0-9]{8}', filename)
    if res:
        return res.group()
    return ''

def add(date, ser):
    dates[date] = 1
    series[ser] = 1

    if date not in results:
        results[date] = {}

    if ser in results[date]:
        value = results[date][ser]
        results[date][ser] = value + 1
    else:
        results[date][ser] = 1

def add_bond_series(filename):
    date = extract_date(filename)
    f = open(filename, 'r')
    for line in f:
        for match in re.finditer(BOND_PATTERN, line):
            ser = extract_series(match.group())
            add(date, ser)
    f.close()

def list_files():
    arr_txt = [FOLDER_WITH_FILES + x for x in os.listdir(FOLDER_WITH_FILES) if x.endswith(".txt")]
    return arr_txt

def get_value(date, ser):
    if ser in results[date]:
        return results[date][ser]
    return ''

files = list_files()
for f in files:
    print('Processing {}'.format(f), flush = True)
    add_bond_series(f)

for date in dates:
    print(',{}'.format(date), end = '', flush = True)
print(',', flush = True)

for ser in series:
    print('{},'.format(ser), end = '', flush = True)
    for date in dates:
        print('{},'.format(get_value(date, ser)), end = '', flush = True)
    print('', flush = True)


