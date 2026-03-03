import { motion } from 'framer-motion';
import './AnimatedBackground.css';

const AnimatedBackground = () => {
  return (
    <div className="bg-animation">
      {[0, 1, 2].map((index) => (
        <motion.div
          key={index}
          className={`bg-gradient bg-gradient-${index + 1}`}
          animate={{
            x: [0, 100, -100, 0],
            y: [0, -100, 100, 0],
            scale: [1, 1.1, 0.9, 1],
          }}
          transition={{
            duration: 20,
            repeat: Infinity,
            ease: 'easeInOut',
            delay: index * 5,
          }}
        />
      ))}
    </div>
  );
};

export default AnimatedBackground;
