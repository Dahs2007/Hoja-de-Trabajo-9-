import { motion, useScroll, useTransform } from 'framer-motion';
import './FloatingShapes.css';

const FloatingShapes = () => {
  const { scrollY } = useScroll();

  const y1 = useTransform(scrollY, [0, 1000], [0, 150]);
  const y2 = useTransform(scrollY, [0, 1000], [0, 100]);
  const y3 = useTransform(scrollY, [0, 1000], [0, 200]);

  const rotate1 = useTransform(scrollY, [0, 1000], [0, 360]);
  const rotate2 = useTransform(scrollY, [0, 1000], [0, -360]);
  const rotate3 = useTransform(scrollY, [0, 1000], [45, 405]);

  return (
    <div className="floating-shapes">
      <motion.div
        className="shape shape-1"
        style={{ y: y1, rotate: rotate1 }}
        animate={{ scale: [1, 1.1, 1] }}
        transition={{ duration: 4, repeat: Infinity, ease: 'easeInOut' }}
      />
      <motion.div
        className="shape shape-2"
        style={{ y: y2, rotate: rotate2 }}
        animate={{ scale: [1, 0.9, 1] }}
        transition={{ duration: 5, repeat: Infinity, ease: 'easeInOut' }}
      />
      <motion.div
        className="shape shape-3"
        style={{ y: y3, rotate: rotate3 }}
        animate={{ scale: [1, 1.15, 1] }}
        transition={{ duration: 6, repeat: Infinity, ease: 'easeInOut' }}
      />
    </div>
  );
};

export default FloatingShapes;
