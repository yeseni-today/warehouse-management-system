/**
 * Created by 22340 on 2016/11/27.
 */

document.write("<script id='fs' type='x-shader/x-fragment'>uniform sampler2D map;uniform vec3 fogColor;uniform float fogNear;uniform float fogFar;varying vec2 vUv;void main() {float depth = gl_FragCoord.z / gl_FragCoord.w;float fogFactor = smoothstep( fogNear, fogFar, depth );gl_FragColor = texture2D( map, vUv );gl_FragColor.w *= pow( gl_FragCoord.z, 20.0 );gl_FragColor = mix( gl_FragColor, vec4( fogColor, gl_FragColor.w ), fogFactor );} </script>" +
    "<script id='vs' type='x-shader/x-vertex'> varying vec2 vUv;void main() {vUv = uv;gl_Position = projectionMatrix * modelViewMatrix * vec4( position, 1.0 );}</script>");

document.write("<script src='../../../resources/js/clouds/Detector.js'></script>");
document.write("<script src='../../../resources/js/clouds/three.min.js'></script>");
document.write("<script src='../../../resources/js/clouds/animate.js'></script>");


/*
 <script id="fs" type="x-shader/x-fragment">
 uniform sampler2D map;
 uniform vec3 fogColor;
 uniform float fogNear;
 uniform float fogFar;
 varying vec2 vUv;

 void main() {
 float depth = gl_FragCoord.z / gl_FragCoord.w;
 float fogFactor = smoothstep( fogNear, fogFar, depth );

 gl_FragColor = texture2D( map, vUv );
 gl_FragColor.w *= pow( gl_FragCoord.z, 20.0 );
 gl_FragColor = mix( gl_FragColor, vec4( fogColor, gl_FragColor.w ), fogFactor );
 }

 </script>
 <script id="vs" type="x-shader/x-vertex">
 varying vec2 vUv;
 void main() {
 vUv = uv;
 gl_Position = projectionMatrix * modelViewMatrix * vec4( position, 1.0 );
 }

 </script>
 <script src="../../../resources/js/clouds/Detector.js"></script>
 <script src="../../../resources/js/clouds/three.min.js"></script>
 <script src="../../../resources/js/clouds/animate.js"></script>*/
