import random, string

idx = ["08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"]
lineCount = [750, 1000, 1500, 2000, 3000, 4000, 5000, 7500, 10000, 15000, 20000, 30000, 50000]

def randChar():
    return random.choice(string.ascii_uppercase + string.digits)

def randString(length):
    return ''.join([randChar() for j in range(length)])

for testId in range(len(idx)):
    lines = lineCount[testId]
    data = [randString(random.randint(5, 100)) for i in range(lines)]

    fp1 = open("sample" + idx[testId] + "-1", "w")
    for line in data:
        print(line, file = fp1)
    fp1.close()

    randomLinesChanges = random.randint(3, 13)
    for _ in range(randomLinesChanges):
        i = random.randint(0, lines - 1)
        randomCharChanges = random.randint(1, 20)
        for __ in range(randomCharChanges):
            j = random.randint(0, len(data[i]) - 1)
            data[i] = data[i][:j] + randChar() + data[i][j + 1:]

    fp2 = open("sample" + idx[testId] + "-2", "w")
    for line in data:
        print(line, file = fp2)
    fp2.close()    
