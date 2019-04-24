import math

def calculateWinningOdds(probabilityOfSingleTicketToWin, numberOfTickets):
    return 1.0 - math.pow(1 - probabilityOfSingleTicketToWin, numberOfTickets)

def winingExpectation(winningOdds):
    return 1.0 / winningOdds

def calculateNoOfTicketsFor(expectedWinningOdds, probabilityOfSingleTicketToWin):
    rawValue = math.log(1 - expectedWinningOdds) / math.log(1.0 - probabilityOfSingleTicketToWin)
    return int(math.floor(rawValue) + 1)

MONEY = 139.0

lotteries = [
    {"name": "Premium Bonds NS&I", "odds": 24500, "ticketPrice": 1},
    {"name": "National Lottery (Lotto) 6n", "odds": 45057473, "ticketPrice": 2},
    {"name": "National Lottery (Lotto) 5n&t", "odds": 7509578, "ticketPrice": 2},
    {"name": "National Lottery (Lotto) 5n", "odds": 144414, "ticketPrice": 2},
    {"name": "National Lottery (Lotto) 4n", "odds": 2179, "ticketPrice": 2},
    {"name": "National Lottery (Thunderball) 5n&t", "odds": 8060598, "ticketPrice": 2},
    {"name": "National Lottery (Thunderball) 5n", "odds": 620046, "ticketPrice": 2},
    {"name": "National Lottery (Thunderball) 4n&t", "odds": 47415, "ticketPrice": 2},
    {"name": "National Lottery (Thunderball) 4n", "odds": 3647, "ticketPrice": 2},
    {"name": "Natiomal Lottery (EuroMillions)", "odds": 139838160, "ticketPrice": 2}
]

for lottery in lotteries:
    print("---------------------------",lottery['name'],"------------------------------------")
    singleTicketWinOdds = 1.0 / lottery['odds']
    noOfTicketsForMoney = int(MONEY / lottery['ticketPrice'])
    noOfTicketsForProb = calculateNoOfTicketsFor(0.5, singleTicketWinOdds)
    winningOdds = calculateWinningOdds(singleTicketWinOdds, noOfTicketsForMoney)

    print("Number of tickets to have winning odds > 0.5 is", noOfTicketsForProb, "at price", (noOfTicketsForProb * lottery['ticketPrice']))
    print("With",MONEY,"you can buy",noOfTicketsForMoney,"tickets, with winning odds:", winningOdds)
    print("Expectation:", winingExpectation(winningOdds),"rounds")
    print()
