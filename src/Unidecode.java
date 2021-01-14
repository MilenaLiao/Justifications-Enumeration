import java.io.*;

public class Unidecode {

    String new_query_num;
    String new_query_num_positive;
    String query_concept;
    String query_num;
    Integer subs_num;

    Integer modu_size;

    //construct
    public Unidecode(String querypath) throws IOException, InterruptedException {
        //module
        COImodule module = new COImodule();
        module.COI(querypath);
        modu_size = module.module_size;
        //decode output
        Terminal.terminal_run("./el2sat_all -i=output.txt -o=out.o -z=out.subs -p=out.ass -a=out.ax");
        //get query massage
        query_concept = module.query_concept;
        query_num = module.query_num;
        read_newquerynums("out.subs");

        write_newquerytxt("out_query.txt");



    }

    private void write_newquerytxt(String filepath) {
        try {
            OutputStream outputStream = new FileOutputStream(filepath);
            //BufferedOutputStream out = new BufferedOutputStream(outputStream);

            //BufferedWriter out = new BufferedWriter(new FileWriter(path));
            PrintWriter pw = new PrintWriter(outputStream);

            pw.println(new_query_num);
            pw.flush();

            pw.close();
            outputStream.close();

        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public void read_newquerynums(String queryFile){
        int subsnum = 0;
        try {
            FileInputStream inputStream = new FileInputStream(queryFile);
            BufferedReader bw = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bw.readLine()) != null) {
                subsnum++;
                if(line.contains(query_concept)){
                    String[] str = line.split(" ");
                    new_query_num_positive = str[0];
                }
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        subs_num = subsnum;
        new_query_num = "-"+new_query_num_positive;
        //
        //System.out.println(new_query_num_positive+"   "+new_query_num);

    }


    public static void main(String[] args) throws IOException, InterruptedException {
        Unidecode undecode = new Unidecode("gene/query/q1.txt");
        //System.out.println(undecode.modu_size + "  "+undecode.query_concept);
        System.out.println(undecode.query_concept);
        System.out.println(undecode.new_query_num);
        System.out.println(undecode.subs_num);
        System.out.println(undecode.modu_size);
    }
}

