/**********************************************************
 * 项目名称：山寨“爱消除”游戏7日教程
 * 作          者：郑敏新
 * 腾讯微博：SuperCube3D
 * 日          期：2013年11月
 * 声          明：版权所有   侵权必究
 * 本源代码供网友研究学习OpenGL ES开发Android应用用，
 * 请勿全部或部分用于商业用途
 ********************************************************/

package elong.CrazyLink.Draw;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.opengl.GLUtils;

import elong.CrazyLink.CrazyLinkConstent;


public class DrawLife {
	
	private IntBuffer   mLifeVertexBuffer;		//顶点坐标数据缓冲
	private IntBuffer   mNumVertexBuffer;		//顶点坐标数据缓冲
    private FloatBuffer   mLifeTextureBuffer;	//顶点纹理数据缓冲
    private FloatBuffer   mNumTextureBuffer;	//顶点纹理数据缓冲
    int vCount=0;							//顶点数量     
    int textureId;							//纹理索引
    int numTextureId;
    
    final int mBitmapW = 64;
    final int mBitmapH = 32;
    final int mFontSize = 24;	//字体大小
    
    float textureRatio;						//为了准确获取纹理图片中的素材对象，需要设置纹理的变换率
    public DrawLife(GL10 gl, int textureId)
    {
    	this.textureId=textureId;
    	initNumTextureBuffer();
    	int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);
		this.numTextureId = textures[0];
    }	
	//顶点坐标数据的初始化
    private void initVertexBuffer(int col)
    {
    	    	
    	int w = 16;
    	int h = 16;
        vCount=6;//顶点的数量，一个正方形用两个三角形表示，共需要6个顶点   
        int deltaX = ((col + 2)*2*w*CrazyLinkConstent.UNIT_SIZE);
        int deltaY = 10*2*h*CrazyLinkConstent.UNIT_SIZE;
        int vertices[]=new int[]//顶点坐标数据数组
        {
           	-w*CrazyLinkConstent.UNIT_SIZE+deltaX,h*CrazyLinkConstent.UNIT_SIZE+deltaY,0,
        	-w*CrazyLinkConstent.UNIT_SIZE+deltaX,-h*CrazyLinkConstent.UNIT_SIZE+deltaY,0,
        	w*CrazyLinkConstent.UNIT_SIZE+deltaX,-h*CrazyLinkConstent.UNIT_SIZE+deltaY,0,
        	w*CrazyLinkConstent.UNIT_SIZE+deltaX,-h*CrazyLinkConstent.UNIT_SIZE+deltaY,0,
        	w*CrazyLinkConstent.UNIT_SIZE+deltaX,h*CrazyLinkConstent.UNIT_SIZE+deltaY,0,
        	-w*CrazyLinkConstent.UNIT_SIZE+deltaX,h*CrazyLinkConstent.UNIT_SIZE+deltaY,0
        };
        //创建顶点坐标数据缓冲
        //int类型占用4个字节，因此转换为byte的数据时需要*4
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());		//设置本地的字节顺序
        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题    	        
        mLifeVertexBuffer = vbb.asIntBuffer();		//转换为int型缓冲
        mLifeVertexBuffer.put(vertices);			//向缓冲区中放入顶点坐标数据
        mLifeVertexBuffer.position(0);				//设置缓冲区起始位置
        return;
    }
    
    private void initNumVertexBuffer()
    {
        vCount=6;//顶点的数量，一个正方形用两个三角形表示，共需要6个顶点
        float deltaX = 160*CrazyLinkConstent.UNIT_SIZE;//((col-3)*64*CrazyLinkConstent.UNIT_SIZE);
        float deltaY = 320*CrazyLinkConstent.UNIT_SIZE;//(((float)row-3 + y)*64*CrazyLinkConstent.UNIT_SIZE);
        int vertices[]=new int[]//顶点坐标数据数组
        {
           	-mBitmapW/2*CrazyLinkConstent.UNIT_SIZE+(int)deltaX,mBitmapH/2*CrazyLinkConstent.UNIT_SIZE+(int)deltaY,0,
        	-mBitmapW/2*CrazyLinkConstent.UNIT_SIZE+(int)deltaX,-mBitmapH/2*CrazyLinkConstent.UNIT_SIZE+(int)deltaY,0,
        	mBitmapW/2*CrazyLinkConstent.UNIT_SIZE+(int)deltaX,-mBitmapH/2*CrazyLinkConstent.UNIT_SIZE+(int)deltaY,0,
        	mBitmapW/2*CrazyLinkConstent.UNIT_SIZE+(int)deltaX,-mBitmapH/2*CrazyLinkConstent.UNIT_SIZE+(int)deltaY,0,
        	mBitmapW/2*CrazyLinkConstent.UNIT_SIZE+(int)deltaX,mBitmapH/2*CrazyLinkConstent.UNIT_SIZE+(int)deltaY,0,
        	-mBitmapW/2*CrazyLinkConstent.UNIT_SIZE+(int)deltaX,mBitmapH/2*CrazyLinkConstent.UNIT_SIZE+(int)deltaY,0
        };
        //创建顶点坐标数据缓冲
        //int类型占用4个字节，因此转换为byte的数据时需要*4
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());		//设置本地的字节顺序
        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题    	        
        mNumVertexBuffer = vbb.asIntBuffer();		//转换为int型缓冲
        mNumVertexBuffer.put(vertices);			//向缓冲区中放入顶点坐标数据
        mNumVertexBuffer.position(0);				//设置缓冲区起始位置
        return;
    }

    
    //顶点纹理数据的初始化    
    private void initTextureBuffer(int witch)
    {
        textureRatio = (float)(1/2.0f);		//图片是2个独立的素材对象组成，每次需要根据witch准确地获取对应的素材
        float textureCoors[]=new float[]	//顶点纹理S、T坐标值数组
	    {
        	witch * textureRatio,0,
        	witch * textureRatio,1,
        	(witch+1) * textureRatio,1,
        	(witch+1) * textureRatio,1,
        	(witch+1) * textureRatio,0,        	
        	witch * textureRatio,0
	    };        
        
        //创建顶点纹理数据缓冲
        //int类型占用4个字节，因此转换为byte的数据时需要*4
        ByteBuffer cbb = ByteBuffer.allocateDirect(textureCoors.length*4);
        cbb.order(ByteOrder.nativeOrder());//设置本地字节顺序
        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        mLifeTextureBuffer = cbb.asFloatBuffer();//转换为int型缓冲
        mLifeTextureBuffer.put(textureCoors);//向缓冲区中放入顶点着色数据
        mLifeTextureBuffer.position(0);//设置缓冲区起始位置
    	return;
    }
    
    private void initNumTextureBuffer()
    {
        float textureCoors[]=new float[]	//顶点纹理S、T坐标值数组
	    {
        	0,0,
        	0,1,
        	1,1,
        	1,1,
        	1,0,        	
        	0,0
	    };        
        
        //创建顶点纹理数据缓冲
        //int类型占用4个字节，因此转换为byte的数据时需要*4
        ByteBuffer cbb = ByteBuffer.allocateDirect(textureCoors.length*4);
        cbb.order(ByteOrder.nativeOrder());//设置本地字节顺序
        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        mNumTextureBuffer = cbb.asFloatBuffer();//转换为int型缓冲
        mNumTextureBuffer.put(textureCoors);//向缓冲区中放入顶点着色数据
        mNumTextureBuffer.position(0);//设置缓冲区起始位置
    	return;
    }
    
	private void bindTexture(GL10 gl, Bitmap bmp)
	{
		gl.glBindTexture(GL10.GL_TEXTURE_2D, numTextureId);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);//指定缩小过滤方法
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);//指定放大过滤方法
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);//指定S坐标轴贴图模式
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);//指定T坐标轴贴图模式
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
		bmp.recycle();
	}


	Bitmap genBitmap(int life)
	{
		//生成的位图要带ALPHA通道信息，否则无法进行透明化处理
		Bitmap bitmap = Bitmap.createBitmap(mBitmapW, mBitmapH, Config.ARGB_4444);
		Canvas canvas = new Canvas(bitmap);

		Paint paint = new Paint();
		paint.setColor(Color.TRANSPARENT);
		canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
		paint.setTextSize(mFontSize);	
		paint.setColor(Color.WHITE);
		String str = Integer.toString(life);			
		canvas.drawText(str, 20, 28, paint);	//书写的位置，根据具体情况可以调整一下			

		return bitmap;
	}
	
	public void drawNumber(GL10 gl, int life)
    {
		life -= 3;
		if (life <= 0) return;
    	Bitmap bmp = genBitmap(life);
    	bindTexture(gl, bmp);
    	initNumVertexBuffer();	//根据col,row初始化顶点坐标

    	//顶点坐标，允许使用顶点数组
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		//为画笔指定顶点坐标数据
        gl.glVertexPointer
        (
    		3,				//每个顶点的坐标数量为3  xyz 
    		GL10.GL_FIXED,	//顶点坐标值的类型为 GL_FIXED
    		0, 				//连续顶点坐标数据之间的间隔
    		mNumVertexBuffer	//顶点坐标数据
        );
        
        //纹理坐标，开启纹理
        gl.glEnable(GL10.GL_TEXTURE_2D);   
        //允许使用纹理数组
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        //为画笔指定纹理uv坐标数据
        gl.glTexCoordPointer
        (
    		2, 					//每个顶点两个纹理坐标数据 S、T
    		GL10.GL_FLOAT, 		//数据类型
    		0, 					//连续纹理坐标数据之间的间隔
    		mNumTextureBuffer		//纹理坐标数据
        );        		
        gl.glBindTexture(GL10.GL_TEXTURE_2D,numTextureId);//为画笔绑定指定名称ID纹理   
        
        //绘制图形
        gl.glDrawArrays
        (
    		GL10.GL_TRIANGLES, 
    		0, 
    		vCount
        );
        gl.glDisable(GL10.GL_TEXTURE_2D);//关闭纹理
    	
    }



    void drawLife(GL10 gl, int pic, int col)
    {   
    	initVertexBuffer(col);	//根据col,row初始化顶点坐标
    	initTextureBuffer(pic);	//根据witch来初始化纹理顶点数据
    	//gl.glTranslatef(col * textureRatio, row * textureRatio, 0);	//在x=col,y=row的位置绘制选定的素材对象        
        //顶点坐标，允许使用顶点数组
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		//为画笔指定顶点坐标数据
        gl.glVertexPointer
        (
    		3,				//每个顶点的坐标数量为3  xyz 
    		GL10.GL_FIXED,	//顶点坐标值的类型为 GL_FIXED
    		0, 				//连续顶点坐标数据之间的间隔
    		mLifeVertexBuffer	//顶点坐标数据
        );
        
        //纹理坐标，开启纹理
        gl.glEnable(GL10.GL_TEXTURE_2D);   
        //允许使用纹理数组
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        //为画笔指定纹理uv坐标数据
        gl.glTexCoordPointer
        (
    		2, 					//每个顶点两个纹理坐标数据 S、T
    		GL10.GL_FLOAT, 		//数据类型
    		0, 					//连续纹理坐标数据之间的间隔
    		mLifeTextureBuffer		//纹理坐标数据
        );        		
        gl.glBindTexture(GL10.GL_TEXTURE_2D,textureId);//为画笔绑定指定名称ID纹理   
        
        //绘制图形
        gl.glDrawArrays
        (
    		GL10.GL_TRIANGLES, 
    		0, 
    		vCount
        );
        gl.glDisable(GL10.GL_TEXTURE_2D);//关闭纹理
    }
    
    public void draw(GL10 gl, int life)
    {
    	int lifeCnt = life;
    	if (life > 3) lifeCnt = 3;
    	for(int i = 0; i < 3; i++)
    	{
    		if(lifeCnt >= 3)
    			drawLife(gl, 1, i);
    		else
    			drawLife(gl, 0, i);
    		lifeCnt++;
    	}
    	drawNumber(gl, life);
    }
}

