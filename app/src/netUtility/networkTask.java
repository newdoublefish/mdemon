package netUtility;

import android.content.Context;
import android.os.AsyncTask;

public class networkTask extends AsyncTask<Void, Void, String>{
	private volatile boolean running = true;
	Context context;
	String message = null;
	private Interface mInterface;
	/**
	 * 构造函数
	 * @param context
	 * @param asyncTaskPerformInterface	 * 
	 */
	public networkTask(Context context,
			Interface mInterface) {
        super();
        this.context = context;
        this.mInterface = mInterface;
    }
		/**
		 * 获取数据
		 */
	@Override
	protected String doInBackground(Void... params) {
		// TODO Auto-generated method stub
		if(running){
			String result =new String();			
			if(mInterface!=null){
	            result=mInterface.perform();
	        }
			return result;
		}else{
			return null;
		}
	}

	public void quitTask()
	{
		running = false;
	}
	
	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
		 running = false;
	}
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub				
		super.onPostExecute(result);
		if(running){
			if(mInterface!=null){
				mInterface.Callback(result);
	        }
		}
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();				
	}
 
	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
		if(!running){
			return ;
		}
	}
	 //执行异步操作的回调接口
    public interface Interface {
        public String perform();
        public void Callback(String result);
    }
   
   }
