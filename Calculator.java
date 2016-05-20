package jv_ex;
import java.io.*;
//import java.util.*;
//import java.util.Arrays;
import java.util.zip.ZipEntry;
//import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Calculator {
	
	public static void main(String[] args) throws IOException{
	BufferedReader d = new BufferedReader(new InputStreamReader(System.in));
	System.out.println("Если данные передаются через файл, введите имя файла и нажмите Enter. Если данные передаются через консоль, просто нажмите Enter");
	String fileName = d.readLine();
	if (fileName.length()>0){
	try {
	Calculator myFile = new Calculator();
	
	      // Запись результата в файл
	      FileOutputStream fout = new FileOutputStream("RESULT.jar");
	      ZipOutputStream zout = new ZipOutputStream(fout);
	      ZipEntry ze = new ZipEntry("RESULT.txt");
	      zout.putNextEntry(ze);
	      zout.write( myFile.fileRead(fileName).getBytes());            
	      zout.closeEntry();
	      zout.close();
	      System.out.println("ГОТОВО! Результат выполнения программы (RESULT.txt) можно найти в файле RESULT.jar");
	    } catch (FileNotFoundException e) {
	      System.out.println("Файл не найден.");
	    }
	}
	else {
		System.out.println("Введите выражение.Например: 2/7+8/9.");	
		String vyr = d.readLine();
		System.out.println(getResult(vyr,1,""));
		
		
	}
}
	public String fileRead (String fileName) throws IOException{
        File f = new File(fileName);
        BufferedReader fin = new BufferedReader(new FileReader(f));
        String line;
        String res ="";
        int countLine=1;
        while ((line = fin.readLine()) != null) 
        {
        	res+=getResult(line,countLine,"");
        	countLine++;       	
        }
       
        return  res;
		 
        }
    
    public static String getResult (String str, int countLine, String res) throws IOException{
        	str = str.replaceAll(" ", "");
        	int countSl=0;
    		int countVych=0;
    		int countUmn=0;
        	int countSlesh = 0;
        	int j = -1;
        	int rch=0;
        	int rz=0;
        	String n1ch ="";
        	String n1z ="";
        	String n2ch ="";
        	String n2z ="";
        	String oper ="";
        	
        	for (char element : str.toCharArray()){
        	    if (element == '+') countSl++;
        	    if (element == '-') countVych++;
        	    if (element == '*') countUmn++;
        	    if (element == '/') countSlesh++;
        	}
        	/*максимальное количество слэшей - три (два знака дроби и один знак операции)
        	 *максимальное количество минусов - три (если обе дроби отрицательные и один знак операции) 
        	 *знак умножения мб только один
        	 *знак сложения мб только один
        	*/
        	// узнаем знак операции
        		if ((countSl == 1)&&(countVych < 3)&&(countUmn == 0)&&(countSlesh == 2)) oper="+";
        	    if ((countSl == 0)&&(countVych > 0)&&(countUmn == 0)&&(countSlesh == 2)) oper="-";
        	    if ((countSl == 0)&&(countVych < 3)&&(countUmn == 1)&&(countSlesh == 2)) oper="*";
        	    if ((countSl == 0)&&(countVych < 3)&&(countUmn == 0)&&(countSlesh == 3)) oper="/";
        	    if (oper.equals("+")||oper.equals("-")||oper.equals("*")||oper.equals("/")){
        	    	// находим числитель первой дроби
            		j = str.indexOf('/'); 
            		n1ch=str.substring(0, j);
            		//убираем его
            		str=str.substring(++j);
            		//находим знаменатель первой дроби
            		int q = str.indexOf(oper); 
            		n1z=str.substring(0, q);
            		//убираем его
            		str=str.substring(++q);
            		//Аналогично со второй дробью
            		int w = str.indexOf('/'); 
            		n2ch=str.substring(0, w);
            		n2z=str.substring(++w);
            		try{
            		int ch1 = Integer.parseInt(n1ch);
                	int z1 = Integer.parseInt(n1z);
                	int ch2 = Integer.parseInt(n2ch);
                	int z2 = Integer.parseInt(n2z);
                	if((z1!=0)&&(z2!=0)){
                	switch (oper){
                	case "*":
                		{
                			rch=ch1*ch2; 
                			rz=z1*z2;
                		} break;
                	case "/":
                		{
                			//на ноль делить нельзя, проверка второй дроби
                			if (ch2==0){
                				System.out.println("Вторая дробь в строке "+countLine+" равна нулю. На ноль делить нельзя.");
                			}
                			else {
                			rch=ch1*z2; 
                			rz=z1*ch2;
                			}
                		} break;
                	case "+": 
                		{
                			rch=ch1*z2+z1*ch2; 
                			rz=z1*z2;
                		} break;
                	case "-":
                		{
                			rch=ch1*z2-ch2*z1; 
                			rz=z1*z2;
                		} break;
                	};
                	
                	        	
               		// Нахождение наибольшего общего делителя
                	    int a=rch; int b=rz;
                	    
                			  
                	            while (b !=0) {
                	              int tmp = a%b;
                	              a = b;
                	              b = tmp;
                	            }
                	    rch/=a; 
                	    rz/=a;
                	    res=res+ "Результат в строке "+countLine+": "+rch+"/"+rz+" \n";
                	    countLine++;
                	}
                	else{
        	    		res=res+"Некорректные входные данные в строке "+countLine+": знаменатель дроби не может быть равен нулю. "+"\n";
           	    		countLine++;	               
        	    	}
                	} catch (ArithmeticException|NumberFormatException  e) {
                		res+=("Ошибка ввода в строке "+countLine+". ");
                		countLine++;
                	}
        	    }
            		 else{
            	    		res=res+"Некорректные входные данные в строке "+countLine+": входные данные должны содержать две дроби и один знак операции. Знаменатель дроби не может быть равен нулю. При делении вторая дробь не может быть равна нулю. "+"\n";
               	    		countLine++;              
            	    	}  
        return  res;
		
        }
}