import java.io.*;
import java.util.ArrayList;

public class COImodule {

    //module
    public ArrayList module_list = new ArrayList<String>();
    public int module_size;

    //axioms
    ArrayList<Integer> num =new ArrayList<>(); //sum
    ArrayList<String> axiom=new ArrayList<>(); //sum
    int subs_size=0; //sum

    //query
    String query_concept;//(implies ... ...)
    String query_num;
    String query_num_positive;

    //read module code and size
    public void read_module_list(String path){
        int num = 0;
        try {
            FileInputStream inputStream = new FileInputStream(path);
            BufferedReader bw = new BufferedReader(new InputStreamReader(inputStream));

            String line=null;
            int flag = 0;
            while ((line = bw.readLine()) != null) {

                //System.out.println(line);
                if (!line.contains("MODULE - AXIOMS:")&&(flag==0)) {
                    continue;
                }
                else if(line.contains("MODULE - AXIOMS:")){
                    flag=1;
                    continue;
                }
                else{
                    module_list.add(line);
                    num++;
                }
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        module_size = num;
    }
    //write into input.txt
    public void write_module_list(String path){

        try {
            OutputStream outputStream = new FileOutputStream(path);
            //BufferedOutputStream out = new BufferedOutputStream(outputStream);

            //BufferedWriter out = new BufferedWriter(new FileWriter(path));
            PrintWriter pw = new PrintWriter(outputStream);
            for (int i=0;i<module_list.size();i++)
            {
                String goal = (String) module_list.get(i);
                pw.println(goal);
                pw.flush();
            }
            pw.close();
            outputStream.close();

        }catch(IOException e){
            e.printStackTrace();
        }

    }
    //turn code into .cel

    public void init_subs(String mapFile){
        File file=new File(mapFile);
        BufferedReader mapreader = null;
        try {
            mapreader=new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = mapreader.readLine()) != null) {
                int s=tempString.indexOf(" ");
                int nu=Integer.valueOf(tempString.substring(0,s));
                String ax=tempString.substring(s+1);
                //System.out.println(""+nu);
                //System.out.println(ax);
                num.add(nu);
                axiom.add(ax);
                subs_size++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void transform_code_cel(String input,String output){
        //decode
        File inputfile = new File(input);
        File outfile=new File(output);
        BufferedReader inputreader = null;
        try {
            if (!outfile.exists()) {
                outfile.createNewFile();
            }
            FileWriter fw = new FileWriter(outfile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            inputreader = new BufferedReader(new FileReader(inputfile));
            String tempString = null;
            while ((tempString = inputreader.readLine()) != null) {
                int tnum=Integer.valueOf(tempString);
                if(num.get(tnum-1)==tnum)  //从0开始
                {
                    String temp=axiom.get(tnum-1);
                    bw.write(temp+"\n");
                }
                else
                {
                    for(int i=0;i<subs_size;i++)
                    {
                        if(num.get(i)==tnum)
                        {
                            String temp=axiom.get(i);
                            bw.write(temp+"\n");
                            break;
                        }
                    }
                }
            }
            bw.close();
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void read_query(String queryFile){
        try {
            FileInputStream inputStream = new FileInputStream(queryFile);
            BufferedReader bw = new BufferedReader(new InputStreamReader(inputStream));
            String line=null;
            while ((line = bw.readLine()) != null) {
                //System.out.println("line:"+line);
                query_num = line;
                query_num_positive = line.substring(1);
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        query_concept = axiom.get(Integer.parseInt(query_num_positive)-443);
        System.out.println(query_num);


    }



    public void COI(String querypath) throws IOException, InterruptedException {
        //Terminal.terminal_run("./el2sat_all -i=not-galen.cel -o=gene.o -p=gene.ass -a=gene.ax -v=gene.v -z=gene.subs");

        //module
        Terminal.terminal_run("./el2sat_all_mina -a=gene.ass -q="+querypath+" -i=gene.o -z -c > all-result.txt");
        read_module_list("all-result.txt");
        write_module_list("input.txt");

        //search module axioms
        init_subs("gene.subs");
        transform_code_cel("input.txt","output.txt");
        read_query(querypath);

        //System.out.println("con:"+(axiom.get(Integer.parseInt(query_num_positive)-2))+ " "+axiom.get(Integer.parseInt(query_num_positive)-1)+axiom.get(Integer.parseInt(query_num_positive)));


        //System.out.println(size);
        //System.out.println(module_list);
    };

    public static void main(String[] args) throws IOException, InterruptedException {
        COImodule module = new COImodule();
        module.COI("not-galen/query/q2.txt");

        System.out.println(module.query_num);
        System.out.println(module.query_num_positive);
        System.out.println(module.query_concept);
        System.out.println(module.axiom.get(Integer.parseInt(module.query_num_positive)));



    }
}