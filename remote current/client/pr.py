def lexgen(len):
	yield"w"
	if(len > 0):
		for ch in [ 'a', 'b', 'c', 'd']:
			for w in lexgen(len-1):
				yield ch + w

for w in lexgen(3):
	print(w)