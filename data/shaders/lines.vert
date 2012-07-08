attribute vec4 a_Position;
attribute vec4 a_Color;
varying vec4 v_Color;
uniform float u_time;

void main()
{                            
   v_Color = a_Color;
   
   gl_Position=a_Position;
   gl_Position.x+=sin(u_time)*0.1;
}           