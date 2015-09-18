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


f3 = open("parameters.txt")
lines3 = f3.readlines()
f3.close()

f4 = open("results.txt","a")
f4.write("Error percentage: %.2f%%" %(amount*100./len(lines1)))
f4.write("%s" %(lines3))
f4.write("\n")
f4.close()

os.system("pause")
