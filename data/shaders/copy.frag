#ifdef GL_ES
precision mediump float;
#endif
varying vec2 v_texCoords;
uniform sampler2D u_texture;

void main()                               
{                                            
  gl_FragColor = texture2D(u_texture, v_texCoords);
  gl_FragColor.r=v_texCoords.y;
}