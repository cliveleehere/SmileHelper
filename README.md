# smilehelper

### My idea 
Some people have trouble reading facial expressions due to Autism or Asperger's.  This difficulty in reading facial expressions can lead to relational issues, which can then affect many different aspects of their lives.  Forming new relationships may be harder, and they may be misunderstood by their peers or their colleagues. At home, their family members may find it difficult to connect with them.

My idea is to utilize on-device machine learning to quickly read the emotions of another person.  There will be a live camera view, where the camera is pointed at another person’s face.  The emotions of that person is displayed via text on the screen in real time.  Augmented reality can be used to display the emotions in other ways.  For instance, for kids, an animated cartoon character can be displayed on the person’s shoulder.  Or, there can be environmental effects such as rain or snow, that may mirror the person’s emotions.

An extension of this idea is to help people train their brains to read emotions on their own and to mirror their own facial experiences to match that of the other person. For this, both the front-facing and back-facing cameras can be used.  On the screen, the user is prompted to match the emotions of the other person.  A single-player, gamified version of this app will allow the user to train on their own.

I believe that such an app would be useful and will enrich the lives of those with this disability.

### How I'll bring it to life

(1) The sample code is in this repo:​ https://github.com/cliveleehere/SmileHelper
Right now, it’s using CameraX and ML Kit firebase for smile detection. In the final version, I’m going to switch out CameraX with ARCore and ML Kit for another machine learning model.

(2) The current ML Kit’s Firebase face detection implementation only has ‘smiling’ classification. I could use some help with finding data & training a new machine learning model. I am leaning towards using AutoML Vision Edge, but I haven’t used it before so I’m not 100% sure whether that’s the direction I should go forward with.
Also, for displaying emotions to the user, I’m planning to display cute animals, and use some free 3d models from Poly. If Google would like to provide 3d models and animations, I think that would look fantastic!

(3) Rough timeline:
November & December - Create app with camera preview. Take some online courses on machine learning.
January & February - Train model & hook it up to app
March - Add 3d models & environmental effects
April - the 2 player mode (show both the front and back cameras) Stretch goal - Single player selfie mode.

### About Me

I’m an android engineer by day. I work at a eCommerce company, and I work on the augmented reality / 3d team, and work with ARCore / Sceneform. I also work on my personal apps by night. My personal website is at ​https://cliveleehere.github.io/​ , which lists some of my apps.

I made a version of this at a hackathon, using Unity and Hololense. Because the machine learning model was on the cloud (using Face API https://azure.microsoft.com/en-us/services/cognitive-services/face/​ ), the performance was highly dependent on the network latency. That version of the app lives in https://github.com/RealityVirtually2019/8-smilehelper


[Cover letter](cover_letter.pdf)