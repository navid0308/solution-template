package com.tigerit.exam;

public class queries {
    private final table T;
    private final table data[];
    private int R;//row col for resultant table
    private int C;
    queries(table t[],String s1,String s2,String s3,String s4){
        this.data=t;
        String temp2[]=s2.split(" ");//FROM table_name *optional short name*
        String t1_name=temp2[1];
        String t1_short_name=temp2[temp2.length-1];
        String temp3[]=s3.split(" ");
        String t2_name=temp3[1];
        String t2_short_name=temp3[temp3.length-1];
        
        table t1 = null,t2 = null;
        int X=0;
        int Y=0;
        String info[]=s4.split(" |\\.");//ON tname tcol = tname tcol [len = 6]
        for(int i=0;i<data.length;i++){
            if(data[i].getName().equals(t1_name)){ //info1 table name matches table from database
                t1=data[i];
                t1.addSName(t1_short_name);
                if(t1.getName().equals(info[1]) || t1.getSName().equals(info[1])){
                    X=getIndex(t1.getColNames(),info[2]);
                }
                else{
                    X=getIndex(t1.getColNames(),info[5]);
                }
            }
            else if(data[i].getName().equals(t2_name)){ //info2 table name matches table from database
                t2=data[i];
                t2.addSName(t2_short_name);
                if(t2.getName().equals(info[1]) || t2.getSName().equals(info[1])){
                    Y=getIndex(t2.getColNames(),info[2]);
                }
                else{
                    Y=getIndex(t2.getColNames(),info[5]);
                }
            }
        }
        
        int r[][]=QueryResult(X,Y,t1,t2);//contains query result for ALL columns; next step is eliminating columns
        
        String TEMP1[]=t1.getColNames();
        String TEMP2[]=t2.getColNames();
        
        String COLNAMES[]=new String[TEMP1.length+TEMP2.length];
        int index=0;
        for(int A=0;A<TEMP1.length;A++){
            COLNAMES[index]=TEMP1[A];
            index++;
        }
        for(int A=0;A<TEMP2.length;A++){
            COLNAMES[index]=TEMP2[A];
            index++;
        }
        if(s1.contains("*")){//case 1: SELECT *
            
            T = new table("result",R,C);
            T.addColName(COLNAMES);
            T.addData(r);
        }
        else{//columns defined after SELECT; trimming down result of SELECT *
            String cNames[] = s1.split(" ");
            String RCols = "";
            for(int i=1;i<cNames.length;i++)
            {
                String s[]=cNames[i].split("\\.|,");//tname colname tname in each cNames[index]
                if(t1.getName().equals(s[0]) || t1.getSName().equals(s[0])){
                    String strTemp[]=t1.getColNames();
                    for(int j=0;j<strTemp.length;j++){
                        if(strTemp[j].equals(s[1])){
                            if(RCols.equals(""))
                                RCols = strTemp[j];
                            else
                                RCols = RCols+" "+strTemp[j];
                        }
                    }
                }
                else if(t2.getName().equals(s[0]) || t2.getSName().equals(s[0])){
                    String strTemp[]=t2.getColNames();
                    for(int j=0;j<strTemp.length;j++){
                        if(strTemp[j].equals(s[1])){
                            if(RCols.equals(""))
                                RCols = strTemp[j];
                            else
                                RCols = RCols+" "+strTemp[j];
                        }
                    }
                }
            }
            String resultCols[] = RCols.split(" ");
            
            for(int i=0;i<resultCols.length;i++){
                for(int j=0;j<COLNAMES.length;j++){
                    if(resultCols[i].equals(COLNAMES[j])){
                        r=swapCol(r,i,j);
                    }
                }
            }
            
            this.C=resultCols.length;
            
            int tempR[][]=r;
            r=null;
            r=new int[R][C];
            for(int i=0;i<R;i++){
                for(int j=0;j<C;j++){
                    r[i][j]=tempR[i][j];
                }
            }
                    
            T = new table("result",R,C);
            T.addColName(resultCols);
            T.addData(r);
        }
    }
    
    public int[][] swapCol(int r[][],int to,int from){
        if(r!=null){
            for(int i=0;i<r.length;i++){
                int temp=r[i][to];
                r[i][to]=r[i][from];
                r[i][from]=temp;
            }
        }
        return r;
    }
    
    public table getTable(){
        return this.T;
    }
    
    public int[][] QueryResult(int t1_colno,int t2_colno,table t1,table t2){
        int t1_data[][]=t1.getData();
        int t2_data[][]=t2.getData();
        int r[][]=null;
        this.R=0;
        this.C=0;
        
        for(int i=0;i<t1.getRow();i++){
            for(int j=0;j<t2.getRow();j++){
                if(t1_data[i][t1_colno]==t2_data[j][t2_colno]){
                    if(r==null){
                        r=new int[1][t1_data[i].length+t2_data[j].length];
                        this.R=1;
                        this.C=t1_data[i].length+t2_data[j].length;
                        int index=0;
                        
                        for(int A=0;A<t1_data[i].length;A++){
                            r[0][index]=t1_data[i][A];
                            index++;
                        }
                        for(int A=0;A<t2_data[j].length;A++){
                            r[0][index]=t2_data[j][A];
                            index++;
                        }
                    }
                    else{
                        int temp[][]=r;
                        r=null;
                        r=new int[temp.length+1][t1_data[i].length+t2_data[j].length];//dynamically increasing rows
                        
                        this.R=temp.length+1;
                        this.C=t1_data[i].length+t2_data[j].length;
                        
                        for(int A=0;A<temp.length;A++){
                            for(int B=0;B<temp[0].length;B++){
                                r[A][B]=temp[A][B];
                            }
                        }
                        
                        int index=0;
                        for(int A=0;A<t1_data[i].length;A++){
                            r[temp.length][index]=t1_data[i][A];
                            index++;
                        }
                        for(int A=0;A<t2_data[j].length;A++){
                            r[temp.length][index]=t2_data[j][A];
                            index++;
                        }
                    }
                }
            }
        }
        return lexicographic(r);
    }
    
    public int[][] lexicographic(int x[][]){
        for(int i=0;i<R-1;i++){
            if(x[i][0]>x[i+1][0]){
                for(int j=0;j<C;j++)
                {
                    int temp=x[i][j];
                    x[i][j]=x[i+1][j];
                    x[i+1][j]=temp;
                }
                i=-1;
            }
            else if(x[i][0]==x[i+1][0]){
                for(int col=1;col<C && x[i][col]>=x[i+1][col];col++){
                    if(x[i][col]>x[i+1][col]){
                        for(int j=0;j<C;j++)
                        {
                            int temp=x[i][j];
                            x[i][j]=x[i+1][j];
                            x[i+1][j]=temp;
                        }
                        i=-1;
                        break;
                    }
                }
            }
        }
        return x;
    }
    
    public int getIndex(String S[],String search){
        int x = 0;
        for(int i=0;i<S.length;i++){
            if(S[i].equals(search)){
                x=i;
                break;
            }
        }
        return x;
    }
}
