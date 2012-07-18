attribute vec4 a_Position;
attribute vec2 a_texCoords;
varying vec2 v_texCoords;
uniform vec4 u_color;
varying vec4 v_color;

void main()
{                            
   v_texCoords = a_texCoords;
   v_color=u_color;
   
   gl_Position=a_Position;
}           