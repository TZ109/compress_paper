import sys,os



temp = os.path.isfile(sys.argv[1])
if temp :


    f = open(sys.argv[1],"r", encoding='UTF8')
    line = f.read();
    print(line);
    f.close();
else : 
    print("파일이 없습니다.")

