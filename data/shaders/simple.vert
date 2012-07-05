attribute vec4 a_Position;
attribute vec4 a_Color;
attribute vec2 a_texCoords;
varying vec4 v_Color;
varying vec2 v_texCoords;

void main()
{                            
   v_Color = a_Color;
   v_texCoords = a_texCoords;
   gl_Position =   a_Position;
}           