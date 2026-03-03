import { AnimatedBackground, GridOverlay, FloatingShapes } from './components/background';
import { Navbar, Footer } from './components/layout';
import { Hero, About, Projects, Education, Skills, Contact } from './components/sections';
import './styles/globals.css';

function App() {
  return (
    <>
      <AnimatedBackground />
      <GridOverlay />
      <FloatingShapes />
      <Navbar />
      <main>
        <Hero />
        <About />
        <Projects />
        <Education />
        <Skills />
        <Contact />
      </main>
      <Footer />
    </>
  );
}

export default App;
