f1 = open("output.txt")
f2 = open("targets.txt")
lines1 = f1.readlines()
lines2 = f2.readlines()
f1.close()
f2.close()
amount = 0
for i in range(len(lines1)):
	if lines1[i]!=lines2[i]:
		amount+=1
print "%d (%.2f%%)" %(amount, amount*100./len(lines1))
import os
os.system("pause")
