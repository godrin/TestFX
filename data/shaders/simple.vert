attribute vec4 a_Position;
attribute vec4 a_Color;
attribute vec2 a_texCoords;
varying vec4 v_Color;
varying vec2 v_texCoords;
uniform float u_time;
uniform mat4 u_world;

void main()
{                            
   v_Color = a_Color;
   v_texCoords = a_texCoords;
   //gl_Position =   a_Position;
   v_texCoords.x+=sin(u_time/60f)*0.5f;
   gl_Position=u_world*a_Position;
}           