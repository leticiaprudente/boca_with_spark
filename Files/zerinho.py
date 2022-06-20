#entry
A, B, C = input().split()

#processing
winner = None
if A!= B and A != C:
	winner = 'A'
elif B != A and B !=C:
  winner = 'B'
elif C != A and C != B:
  winner = 'C'
else:
  winner = '*'
  
#exit
print(winner)