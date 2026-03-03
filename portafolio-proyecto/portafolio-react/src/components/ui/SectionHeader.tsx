import { motion } from 'framer-motion';
import './SectionHeader.css';

interface SectionHeaderProps {
  tag: string;
  title: string;
  description?: string;
}

const SectionHeader = ({ tag, title, description }: SectionHeaderProps) => {
  return (
    <motion.div
      className="section-header"
      initial={{ opacity: 0, y: 30 }}
      whileInView={{ opacity: 1, y: 0 }}
      viewport={{ once: true, margin: '-100px' }}
      transition={{ duration: 0.6 }}
    >
      <span className="section-tag">{tag}</span>
      <h2 className="section-title">{title}</h2>
      {description && <p className="section-description">{description}</p>}
    </motion.div>
  );
};

export default SectionHeader;
