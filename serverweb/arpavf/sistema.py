fin = open("scrivi2020.txt","r")
cout = 0

insert = "insert into pm10(stazioni_codseqst, stazioni_misurazioni_pm10_data, stazioni_misurazioni_pm10_mis) values \n"

riga = insert

for i in fin:

    cout += 1
    if( cout == 26 ):
        cout = 0
        riga += insert

    else:
        if( cout == 25 ): 
            riga += (i[:(len(i)-2)] + ";\n\n")
            
        else:
            riga += i

fin.close()

fout = open("scrivinuovo2020.txt", "w")
fout.write(riga)
fout.close()