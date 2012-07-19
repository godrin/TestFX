#ifdef GL_ES
precision mediump float;
#endif
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform int blurWidth;
uniform float textureWidth;
uniform float sigma;
const float pi = 3.14159265f;

void main()                               
{
  vec3 incrementalGaussian;
  incrementalGaussian.x = 1.0f / (sqrt(2.0f * pi) * sigma);
  incrementalGaussian.y = exp(-0.5f / (sigma * sigma));
  incrementalGaussian.z = incrementalGaussian.y * incrementalGaussian.y;
  
  float coefficientSum = 0.0f;                                          
  vec4 colorSum;
  int i;
  vec2 tc0=v_texCoords;
  vec2 tc1=v_texCoords;
  
  colorSum+=texture2D(u_texture, tc0) * incrementalGaussian.x;
  coefficientSum += incrementalGaussian.x;
  incrementalGaussian.xy *= incrementalGaussian.yz;
  
  for(i=1;i<=blurWidth;i++) {
    tc0.x+=1.0f/textureWidth;
    tc1.x-=1.0f/textureWidth;
    colorSum+=texture2D(u_texture, tc0) * incrementalGaussian.x;
    colorSum+=texture2D(u_texture, tc1) * incrementalGaussian.x;
    coefficientSum+=incrementalGaussian.x;
    incrementalGaussian.xy *= incrementalGaussian.yz;
  }
  gl_FragColor=colorSum/coefficientSum;
}