import { motion } from 'framer-motion';
import { MapPin } from 'lucide-react';
import { Button, Card3D, TechBadge } from '../ui';
import { personalInfo, mainStack } from '../../data/personal';
import './Hero.css';

const Hero = () => {
  const containerVariants = {
    hidden: { opacity: 0 },
    visible: {
      opacity: 1,
      transition: {
        staggerChildren: 0.15,
      },
    },
  };

  const itemVariants = {
    hidden: { opacity: 0, x: -50 },
    visible: { opacity: 1, x: 0 },
  };

  return (
    <section className="hero">
      <div className="hero-container">
        <motion.div
          className="hero-content"
          variants={containerVariants}
          initial="hidden"
          animate="visible"
        >
          <motion.div className="hero-badge" variants={itemVariants}>
            <MapPin size={14} /> Disponible para trabajar
          </motion.div>

          <motion.h1 className="hero-title" variants={itemVariants}>
            Desarrollo
            <span className="gradient-text"> soluciones digitales </span>
            que importan
          </motion.h1>

          <motion.p className="hero-subtitle" variants={itemVariants}>
            {personalInfo.subtitle}
          </motion.p>

          <motion.div className="hero-cta" variants={itemVariants}>
            <Button href="#proyectos" variant="primary">
              Ver proyectos
            </Button>
            <Button href="#contacto" variant="secondary">
              Contáctame
            </Button>
          </motion.div>
        </motion.div>

        <div className="hero-card-container">
          <Card3D>
            <div className="card-header">
              <h3>// Stack Principal</h3>
            </div>
            <div className="card-tech-stack">
              {mainStack.map((tech, index) => (
                <TechBadge key={tech} delay={0.8 + index * 0.1}>
                  {tech}
                </TechBadge>
              ))}
            </div>
            <div className="stats-grid">
              <motion.div
                className="stat-item"
                whileHover={{ y: -5, backgroundColor: 'rgba(3, 105, 161, 0.06)' }}
              >
                <div className="stat-number">UVG</div>
                <div className="stat-label">Ciencias de la Computación</div>
              </motion.div>
              <motion.div
                className="stat-item"
                whileHover={{ y: -5, backgroundColor: 'rgba(3, 105, 161, 0.06)' }}
              >
                <div className="stat-number">{personalInfo.stats.yearsExperience}+</div>
                <div className="stat-label">Años programando</div>
              </motion.div>
              <motion.div
                className="stat-item full-width"
                whileHover={{ y: -5, backgroundColor: 'rgba(3, 105, 161, 0.06)' }}
              >
                <div className="stat-number">{personalInfo.stats.projectsCompleted}</div>
                <div className="stat-label">Proyectos full stack</div>
              </motion.div>
            </div>
          </Card3D>
        </div>
      </div>
    </section>
  );
};

export default Hero;
