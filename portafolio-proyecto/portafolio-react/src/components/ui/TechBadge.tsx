import { motion } from 'framer-motion';
import './TechBadge.css';

interface TechBadgeProps {
  children: React.ReactNode;
  delay?: number;
}

const TechBadge = ({ children, delay = 0 }: TechBadgeProps) => {
  return (
    <motion.span
      className="tech-badge"
      initial={{ opacity: 0, scale: 0.8 }}
      animate={{ opacity: 1, scale: 1 }}
      transition={{ delay, type: 'spring', stiffness: 300 }}
      whileHover={{ y: -2, scale: 1.05 }}
    >
      {children}
    </motion.span>
  );
};

export default TechBadge;
