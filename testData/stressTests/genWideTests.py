import random, string

lineCount = [100, 125, 150, 175, 200, 250, 300, 350, 400, 500]
minLineLen = [100, 200, 300, 400, 500, 600, 700, 800, 900, 1000]


def randChar():
    return random.choice(string.ascii_uppercase + string.digits)


def randString(length):
    return ''.join([randChar() for j in range(length)])


for testId in range(len(lineCount)):
    lines = lineCount[testId]
    data = [randString(random.randint(minLineLen[testId], 2 * minLineLen[testId])) for i in range(lines)]

    testName = "wide" + str(lines)

    fp1 = open(testName + "-1", "w")
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

    fp2 = open(testName + "-2", "w")
    for line in data:
        print(line, file = fp2)
    fp2.close()    
