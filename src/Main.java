import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        try {
            OutputStream outputStream = new FileOutputStream("excel.txt");
            //BufferedOutputStream out = new BufferedOutputStream(outputStream);

            //BufferedWriter out = new BufferedWriter(new FileWriter(path));
            PrintWriter pw = new PrintWriter(outputStream);




            for (int k=1;k<=1;k++) {
                //if(k==155 ){
                    //continue;
                //}
                String cnf = "CNF.cnf";
                String path = "nci/query/q" + k + ".txt";

                Long t11 = System.nanoTime();
                Unidecode unidecode = new Unidecode(path);
                CNFdecode cnfdecode = new CNFdecode(unidecode);
                cnfdecode.writeCNF(cnf);
                Long t22 = System.nanoTime();

                System.out.println(unidecode.query_num);



                //System.out.println(cnfdecode.clause_num);

                //System.out.println("unimus:");
                long c1 = 0;
                for (int i = 0; i < 5; i++) {
                    Long t1 = System.nanoTime();
                    Terminal.terminal_run("./UNIMUS/unimus " + cnf);
                    Long t2 = System.nanoTime();
                    c1 = c1 + (t2 - t1);
                }
                System.out.println(c1 / 5);
                //pw.println(c1 / 5);

                //System.out.println("CAMUS:");
                long c3 = 0;
                for (int i = 0; i < 5; i++) {
                    Long t1 = System.nanoTime();
                    Terminal.terminal_run("./CAMUS/mcs/camus_mcs " + cnf + " | ./CAMUS/mus/camus_mus");

                    //Terminal.terminal_run("./CAMUS/mcs/camus_mcs "+cnf+" > real.MCSes");
                    //Terminal.terminal_run("./CAMUS/mus/camus_mus "+cnf+" > real.MUSes");
                    Long t2 = System.nanoTime();
                    c3 = c3 + (t2 - t1);
                }
                System.out.println(c3 / 5);
                //pw.println(c3/5);

                //System.out.println("marco:");
                long c2 = 0;
                for (int i = 0; i < 5; i++) {
                    Long t1 = System.nanoTime();
                    Terminal.terminal_run("./MARCO/marco.py " + cnf);
                    Long t2 = System.nanoTime();
                    c2 = c2 + (t2 - t1);
                }
                System.out.println(c2 / 5);
                //pw.println(c2/5);
                pw.println(unidecode.query_num+"          "+cnfdecode.clause_num+"               "+(c1/5)+"                 "+(c3/5)+"               "+(c2/5));
                pw.flush();
            }

            pw.close();
            outputStream.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
