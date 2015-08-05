package tools.file.xls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class XlsReader {

	/**
	 * 读取xls文件内容
	 * 
	 * @param file
	 *            想要读取的文件对象
	 * @return 返回文件内容
	 */
	public static String xls2String(File file) {
		String result = "";
		try {
			FileInputStream fis = new FileInputStream(file);
			StringBuilder sb = new StringBuilder();
			jxl.Workbook rwb = Workbook.getWorkbook(fis);
			Sheet[] sheet = rwb.getSheets();
			for (int i = 0; i < sheet.length; i++) {
				Sheet rs = rwb.getSheet(i);
				Map rows = new HashMap();
				int startRow = 0;
				String startItem = "";
				for (int j = 0; j < rs.getRows(); j++) {
					Cell[] cells = rs.getRow(j);
					String[] cols = new String[12];
					/*for (int k = 0; k < cells.length; k++){
						sb.append(cells[k].getContents()+"+++++++++"+k+"+++++++++++");
					}*/
					String item = cells[1].getContents();
					if(!startItem.equals(item)){
						startItem = item;
						startRow = j;
						cols[0]=(cells[0].getContents());
						cols[1]=(cells[1].getContents());
						cols[2]=(cells[2].getContents());
						cols[3]=(cells[3].getContents());
						cols[4]=(cells[4].getContents());
						cols[5]=(cells[5].getContents());
						cols[6]=(cells[6].getContents());
						cols[7]=(cells[7].getContents());
						cols[8]=(cells[8].getContents());
						cols[9]=(cells[9].getContents());
						cols[10]=(cells[10].getContents());
						cols[11]=(cells[11].getContents());
						
						rows.put(j, cols);
						
					}else{
						//String dept1 = (String)((String[])rows.get(startRow))[1];
						String dept2 = (String)((String[])rows.get(startRow))[6];
						//((String[])rows.get(startRow))[1] = (dept1 += "、"+cells[1].getContents());
						((String[])rows.get(startRow))[6] = (dept2 += "、"+cells[6].getContents());
					}
						
				}
				System.out.println(rows.size());
				
				saveToXls(rows);
				
			}
			fis.close();
			result += sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void saveToXls(Map map) {
		WritableWorkbook wwb;
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(new File("d:\\xxx.xls"));
			wwb = Workbook.createWorkbook(fos);
			WritableSheet sheet = wwb.createSheet("result", 0);
			Set keys = map.keySet();
			Iterator it = keys.iterator();
			int i = 0;
			Label lable = null;
			while(it.hasNext()){
				
				int key = (Integer)it.next();
				String[] row = (String[])map.get(key);
				for(int j = 0;j<row.length;j++){
					lable = new Label(j, i, row[j]);
					sheet.addCell(lable);
				}
				i++;
			}
			wwb.write();
			wwb.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		File file = new File("D:\\bl\\接收\\段学智\\指标导出20150720\\t_indicator.xls");
		System.out.println(xls2String(file));
	}

}
