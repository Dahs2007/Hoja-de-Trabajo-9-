import { motion } from 'framer-motion';
import type { Project } from '../../types';
import './ProjectCard.css';

interface ProjectCardProps {
  project: Project;
  index: number;
  reverse?: boolean;
}

const ProjectCard = ({ project, index, reverse = false }: ProjectCardProps) => {
  const typeLabels = {
    university: 'Proyecto Universitario',
    internship: 'Pasantía',
    personal: 'Proyecto Personal',
  };

  return (
    <motion.article
      className={`project-card ${reverse ? 'reverse' : ''}`}
      initial={{ opacity: 0, y: 50 }}
      whileInView={{ opacity: 1, y: 0 }}
      viewport={{ once: true, margin: '-100px' }}
      transition={{ duration: 0.6, delay: index * 0.1 }}
      whileHover={{ y: -10 }}
    >
      <div className="project-content">
        <div className="project-info">
          <div className="project-meta">
            <span className="project-type">{typeLabels[project.type]}</span>
            <span className="project-date">{project.date}</span>
          </div>
          <h3>{project.title}</h3>
          <p className="project-description">{project.description}</p>
          <div className="project-tags">
            {project.tags.map((tag, i) => (
              <span key={i} className="project-tag">
                {tag}
              </span>
            ))}
          </div>
          <div className="project-links">
            {project.liveUrl && project.liveUrl !== '#' && (
              <a href={project.liveUrl} className="project-link" target="_blank" rel="noopener noreferrer">
                Ver proyecto →
              </a>
            )}
            {project.githubUrl && project.githubUrl !== '#' && (
              <a href={project.githubUrl} className="project-link" target="_blank" rel="noopener noreferrer">
                GitHub →
              </a>
            )}
          </div>
        </div>
        <motion.div
          className="project-image-wrapper"
          whileHover={{ scale: 1.02 }}
          transition={{ type: 'spring', stiffness: 300 }}
        >
          <img src={project.image} alt={project.title} className="project-image" />
          <div className="project-overlay" />
        </motion.div>
      </div>
    </motion.article>
  );
};

export default ProjectCard;
