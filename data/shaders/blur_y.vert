attribute vec4 a_Position;
attribute vec2 a_texCoords;
varying vec2 v_texCoords;

void main()
{                            
   v_texCoords = a_texCoords;
   
   gl_Position=a_Position;
}           