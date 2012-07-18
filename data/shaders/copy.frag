#ifdef GL_ES
precision mediump float;
#endif
varying vec2 v_texCoords;
varying vec4 v_color;
uniform sampler2D u_texture;

void main()                               
{ 
  vec4 c= texture2D(u_texture, v_texCoords);
                                             
  gl_FragColor=c*v_color;
  
}