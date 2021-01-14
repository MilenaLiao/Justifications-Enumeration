import java.io.*;
import java.util.ArrayList;

public class CNFdecode {

    Integer modu_size;
    Integer var_size;
    String query_num_positive;
    Integer clause_num;
    public ArrayList constraints = new ArrayList<String>();

    public CNFdecode(Unidecode unidecode){
        modu_size = unidecode.modu_size;
        query_num_positive = unidecode.new_query_num_positive;
        var_size = unidecode.subs_num;
        read_constraints("out.o");
        clause_num=constraints.size()+modu_size+1;

    }

    //read out.o
    private void read_constraints(String path) {
        File file=new File(path);
        BufferedReader mapreader = null;
        try {
            mapreader=new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = mapreader.readLine()) != null) {
                constraints.add(tempString);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(constraints);
    }

    //write into input.txt
    public void writeCNF(String wpath){

        try {
            OutputStream outputStream = new FileOutputStream(wpath);
            //BufferedOutputStream out = new BufferedOutputStream(outputStream);

            //BufferedWriter out = new BufferedWriter(new FileWriter(path));
            PrintWriter pw = new PrintWriter(outputStream);
            int clause_size=constraints.size()+modu_size+1;
            pw.println("p cnf "+var_size+" "+clause_size);
            pw.flush();
            for (int i=1;i<=modu_size;i++)
            {
                pw.println(i+" 0");
                pw.flush();
            }
            for (int j=0;j<constraints.size();j++){
                pw.println(constraints.get(j));
                pw.flush();
            }
            pw.println("-"+query_num_positive+" 0");
            pw.close();
            outputStream.close();

        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String path ="not-galen/query/q2.txt";
        Unidecode unidecode = new Unidecode(path);
        CNFdecode cnfdecode = new CNFdecode(unidecode);
        System.out.println(cnfdecode.constraints);
        System.out.println(cnfdecode.var_size);
        System.out.println(cnfdecode.constraints.size());
        System.out.println(unidecode.new_query_num);
        System.out.println(cnfdecode.modu_size);

        cnfdecode.writeCNF("CNF.cnf");




    }
}
