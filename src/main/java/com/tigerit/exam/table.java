package main.java.com.tigerit.exam;
import static main.java.com.tigerit.exam.IO.*;

public class table {
    private String name;
    private String short_name;
    private String col_names[];
    private int data[][];
    private int row,col;
    
    table(String n, int r, int c) {
        this.name=n;
        col_names = new String[col];
        data = new int[row][col];
        this.row=r;
        this.col=c;
    }
    
    public void addSName(String s){
        this.short_name=s;
    }
    
    public void addColName(String S[]){
        this.col_names=S;
    }
    
    public void addData(int X[][]){
        this.data=X;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getSName(){
        return this.short_name;
    }
    
    public String[] getColNames(){
        return this.col_names;
    }
    
    public int getRow(){
        return this.row;
    }
    
    public int getCol(){
        return this.col;
    }
    
    public int[][] getData(){
        return this.data;
    }
    
    public void printTable(){
        //print column names
        for(int i=0;i<col_names.length;i++){
            System.out.print(col_names[i]);
            if(i<col_names.length-1){
                System.out.print(" ");
            }
        }
        System.out.println();
        //printing the data
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                System.out.print(data[i][j]);
                if(j<col-1){
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
