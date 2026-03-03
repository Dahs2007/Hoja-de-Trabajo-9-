import { motion } from 'framer-motion';
import type { Skill } from '../../types';
import { iconMap } from '../../utils/iconMap';
import './SkillCard.css';

interface SkillCardProps {
  skill: Skill;
  index: number;
}

const SkillCard = ({ skill, index }: SkillCardProps) => {
  const Icon = iconMap[skill.icon];

  return (
    <motion.div
      className="skill-card"
      initial={{ opacity: 0, y: 30 }}
      whileInView={{ opacity: 1, y: 0 }}
      viewport={{ once: true, margin: '-50px' }}
      transition={{ duration: 0.5, delay: index * 0.1 }}
      whileHover={{ y: -5 }}
    >
      <div className="skill-content">
        <span className="skill-icon">
          {Icon && <Icon size={28} strokeWidth={1.5} />}
        </span>
        <h3 className="skill-title">{skill.title}</h3>
        <ul className="skill-items">
          {skill.items.map((item, i) => (
            <motion.li
              key={i}
              className="skill-item"
              initial={{ opacity: 0, x: -10 }}
              whileInView={{ opacity: 1, x: 0 }}
              viewport={{ once: true }}
              transition={{ delay: index * 0.1 + i * 0.05 }}
            >
              {item}
            </motion.li>
          ))}
        </ul>
      </div>
    </motion.div>
  );
};

export default SkillCard;
