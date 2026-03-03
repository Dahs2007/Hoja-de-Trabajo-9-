import { motion } from 'framer-motion';
import { User, Lightbulb, Palette, Target, Briefcase } from 'lucide-react';
import { SectionHeader } from '../ui';
import { personalInfo } from '../../data/personal';
import { experience } from '../../data/education';
import './About.css';

const About = () => {
  const cardVariants = {
    hidden: { opacity: 0, y: 30 },
    visible: { opacity: 1, y: 0 },
  };

  return (
    <section id="sobre-mi" className="about">
      <SectionHeader
        tag="// Sobre mí"
        title="Conóceme"
        description="Mi historia, pasión y lo que me motiva a crear"
      />

      <div className="about-content">
        <motion.div
          className="about-main"
          initial="hidden"
          whileInView="visible"
          viewport={{ once: true, margin: '-100px' }}
          transition={{ staggerChildren: 0.15 }}
        >
          <motion.div className="about-card" variants={cardVariants}>
            <div className="about-card-icon">
              <User size={28} strokeWidth={1.5} />
            </div>
            <h3>Mi Historia</h3>
            <p>{personalInfo.about.intro}</p>
          </motion.div>

          <motion.div className="about-card" variants={cardVariants}>
            <div className="about-card-icon">
              <Lightbulb size={28} strokeWidth={1.5} />
            </div>
            <h3>Mi Pasión</h3>
            <p>{personalInfo.about.passion}</p>
          </motion.div>

          <motion.div className="about-card" variants={cardVariants}>
            <div className="about-card-icon">
              <Palette size={28} strokeWidth={1.5} />
            </div>
            <h3>Mi Estilo</h3>
            <p>{personalInfo.about.style}</p>
          </motion.div>

          <motion.div className="about-card hobbies-card" variants={cardVariants}>
            <div className="about-card-icon">
              <Target size={28} strokeWidth={1.5} />
            </div>
            <h3>Mis Hobbies</h3>
            <div className="hobbies-list">
              {personalInfo.about.hobbies.map((hobby, index) => (
                <motion.span
                  key={hobby}
                  className="hobby-tag"
                  initial={{ opacity: 0, scale: 0.8 }}
                  whileInView={{ opacity: 1, scale: 1 }}
                  viewport={{ once: true }}
                  transition={{ delay: 0.3 + index * 0.1 }}
                  whileHover={{ scale: 1.05 }}
                >
                  {hobby}
                </motion.span>
              ))}
            </div>
          </motion.div>
        </motion.div>

        <motion.div
          className="experience-card"
          initial={{ opacity: 0, x: 50 }}
          whileInView={{ opacity: 1, x: 0 }}
          viewport={{ once: true, margin: '-100px' }}
          transition={{ duration: 0.6 }}
        >
          <div className="experience-header">
            <span className="experience-icon">
              <Briefcase size={32} strokeWidth={1.5} />
            </span>
            <div>
              <h3>Experiencia</h3>
              <span className="experience-type">Pasantía</span>
            </div>
          </div>
          <h4 className="experience-title">{experience.title}</h4>
          <p className="experience-period">{experience.period}</p>
          <p className="experience-description">{experience.description}</p>
        </motion.div>
      </div>
    </section>
  );
};

export default About;
