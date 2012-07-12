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
  float textureWidth=1024.0f;
  float weight;
  vec2 tc=v_texCoords;
  
  tc.y-=float(blurWidth)/textureWidth;
  
  for(i=-blurWidth;i<=blurWidth;i++) {
    tc.y+=1.0f/textureWidth;
    weight=float(blurWidth)-abs(float(i));
    currentColor=texture2D(u_texture, tc)*weight; //((float)blurWidth-abs((float)i*1.0f)));
    colorSum+=currentColor;
  }
  colorSum/=float(blurWidth*5);
  gl_FragColor=colorSum;
}