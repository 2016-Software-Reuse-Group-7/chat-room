package save;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*; 

public class SaveFile extends JFrame implements ActionListener {
    JFileChooser chooser;
    FileFilter filter; 
    static String content="2333";
    public static void main(String arg[]) {
        new SaveFile();
    }

    public SaveFile() {
        JButton button;
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setLocation(250, 150);
        Container pane = getContentPane();
        pane.setLayout(new FlowLayout());

        button = new JButton("Save");
        button.addActionListener(this);
        pane.add(button);
        pack();
        chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
     
        chooser.setFileFilter(filter);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        int retval;
        String selection = e.getActionCommand();
        if (selection.equals("Save")) {
            retval = chooser.showSaveDialog(this);
            if (retval == JFileChooser.APPROVE_OPTION) {
            String file=chooser.getSelectedFile().toString();
            
            String d="a.txt";
            File f=new File(file,d);
            System.out.println(file+d);
            try {
				f.createNewFile();
		    	FileOutputStream o=null;
		    	o = new FileOutputStream(f);
		    	o.write(content.getBytes("UTF-8"));
		    	o.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            }
        }
    }
   
  
}
