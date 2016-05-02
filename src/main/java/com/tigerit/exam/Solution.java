package com.tigerit.exam;


import static com.tigerit.exam.IO.*;

/**
 * All of your application logic should be placed inside this class.
 * Remember we will load your application from our custom container.
 * You may add private method inside this class but, make sure your
 * application's execution points start from inside run method.
 */
public class Solution implements Runnable {
    @Override
    
    public void run() {
        // your application entry point LEXICOGRAPHIC ORDERRRRRR BAKI
        Integer T=readLineAsInteger();
        for(int i=0;i<T;i++)
        {
            int nT = readLineAsInteger(); //num of tables
            table t[]=new table[nT]; //this array will hold all the tables
            for(int j=0;j<nT;j++){
                t[j]=readTable();
            }
            int nQ = readLineAsInteger();
            queries Q[]=new queries[nQ];
            for(int j=0;j<nQ;j++){
                Q[j]=readQuery(t);
            }
            printLine("Test: "+(i+1));
            for(int j=0;j<nQ;j++){
                Q[j].getTable().printTable();
            }            
        }
    }
    
    public static queries readQuery(table t[]){//separates queries line by line
        String First = readLine();
        String Second = readLine();
        String Third = readLine();
        String Fourth = readLine();
        
        readLine();//handles the blank line after each query
        
        return new queries(t,First,Second,Third,Fourth);
    }
    
    public static table readTable(){
        table t = null; 
        
        String name = readLine();
        String temp2[] = readLine().split(" ");//easier to handle inputs where ints are separated by space (1 2 3)
        int col = Integer.parseInt(temp2[0]);
        int row = Integer.parseInt(temp2[1]);
        String cNames[] = new String[col];
        int X[][]=new int[row][col];
        t = new table(name,row,col);

        cNames=readLine().split(" ");

        for(int a=0;a<row;a++){
            String temp[]=readLine().split(" ");//easier to handle inputs where ints are separated by space (1 2 3)
            for(int b=0;b<col;b++){
                X[a][b]=Integer.parseInt(temp[b]);
            }
        }
        t.addColName(cNames);
        t.addData(X);
        return t;
    }
}
