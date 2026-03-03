import { motion } from 'framer-motion';
import { SectionHeader } from '../ui';
import { education, achievement } from '../../data/education';
import { personalInfo } from '../../data/personal';
import { iconMap } from '../../utils/iconMap';
import './Education.css';

const Education = () => {
  return (
    <section className="education section-dark">
      <SectionHeader
        tag="// Formación"
        title="Educación"
        description="Mi trayectoria académica y logros que fundamentan mi desarrollo profesional"
      />

      <div className="education-content">
        <div className="education-cards">
          {education.map((edu, index) => {
            const Icon = iconMap[edu.icon];
            return (
              <motion.div
                key={edu.institution}
                className="education-card"
                initial={{ opacity: 0, y: 30 }}
                whileInView={{ opacity: 1, y: 0 }}
                viewport={{ once: true, margin: '-50px' }}
                transition={{ duration: 0.5, delay: index * 0.15 }}
                whileHover={{ y: -5 }}
              >
                <span className="education-icon">
                  {Icon && <Icon size={32} strokeWidth={1.5} />}
                </span>
                <div className="education-info">
                  <div className="education-header">
                    <h3>{edu.institution}</h3>
                    <span className={`education-status ${edu.status}`}>
                      {edu.status === 'in-progress' ? 'En curso' : 'Completado'}
                    </span>
                  </div>
                  <p className="education-degree">{edu.degree}</p>
                  <p className="education-period">{edu.period}</p>
                  <div className="education-highlights">
                    {edu.highlights.map((highlight, i) => (
                      <motion.div
                        key={i}
                        className="highlight-item"
                        initial={{ opacity: 0, x: -10 }}
                        whileInView={{ opacity: 1, x: 0 }}
                        viewport={{ once: true }}
                        transition={{ delay: index * 0.15 + i * 0.05 }}
                      >
                        <span className="highlight-arrow">//</span>
                        {highlight}
                      </motion.div>
                    ))}
                  </div>
                </div>
              </motion.div>
            );
          })}
        </div>

        <motion.div
          className="stats-section"
          initial={{ opacity: 0, y: 30 }}
          whileInView={{ opacity: 1, y: 0 }}
          viewport={{ once: true }}
          transition={{ duration: 0.5 }}
        >
          <div className="stat-box">
            <div className="stat-value">{personalInfo.stats.yearsExperience}+</div>
            <div className="stat-text">Años programando</div>
          </div>
          <div className="stat-box">
            <div className="stat-value">{personalInfo.stats.projectsCompleted}+</div>
            <div className="stat-text">Proyectos completados</div>
          </div>
          <div className="stat-box">
            <div className="stat-value">{personalInfo.stats.technologies}+</div>
            <div className="stat-text">Tecnologías</div>
          </div>
          <div className="stat-box">
            <div className="stat-value">{personalInfo.stats.englishLevel}</div>
            <div className="stat-text">Inglés (Intermedio-Avanzado)</div>
          </div>
        </motion.div>

        <motion.div
          className="achievement-card"
          initial={{ opacity: 0, scale: 0.95 }}
          whileInView={{ opacity: 1, scale: 1 }}
          viewport={{ once: true }}
          transition={{ duration: 0.5 }}
          whileHover={{ scale: 1.02 }}
        >
          {(() => {
            const AchievementIcon = iconMap[achievement.icon];
            return (
              <span className="achievement-icon">
                {AchievementIcon && <AchievementIcon size={48} strokeWidth={1.5} />}
              </span>
            );
          })()}
          <div className="achievement-info">
            <h3>{achievement.title}</h3>
            <p className="achievement-institution">{achievement.institution}</p>
            <span className="achievement-category">{achievement.category}</span>
            <p className="achievement-description">{achievement.description}</p>
            <p className="achievement-date">{achievement.date}</p>
          </div>
        </motion.div>
      </div>
    </section>
  );
};

export default Education;
