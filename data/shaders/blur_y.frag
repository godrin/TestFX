#ifdef GL_ES
precision mediump float;
#endif
varying vec2 v_texCoords;
uniform sampler2D u_texture;

void main()                               
{                                            
  vec4 colorSum;
  vec4 currentColor;
  int i;
  int blurWidth=15;
  float textureWidth=1024;
  
  v_texCoords.y-=blurWidth/textureWidth;
  
  for(i=-blurWidth;i<=blurWidth;i++) {
    v_texCoords.y+=1.0f/textureWidth;
    currentColor=texture2D(u_texture, v_texCoords)*(blurWidth-abs(i*1.0f));
    colorSum+=currentColor;
  }
  colorSum/=blurWidth*5;
  gl_FragColor=colorSum;  
}