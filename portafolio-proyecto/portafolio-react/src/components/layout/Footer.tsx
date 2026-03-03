import { motion } from 'framer-motion';
import { Heart } from 'lucide-react';
import { personalInfo } from '../../data/personal';
import './Footer.css';

const Footer = () => {
  const currentYear = new Date().getFullYear();

  return (
    <motion.footer
      className="footer"
      initial={{ opacity: 0 }}
      whileInView={{ opacity: 1 }}
      viewport={{ once: true }}
      transition={{ duration: 0.5 }}
    >
      <div className="footer-content">
        <p className="footer-text">
          © {currentYear} — Desarrollado con <Heart size={14} /> en {personalInfo.location}
        </p>
        <div className="footer-links">
          <a
            href={`https://github.com/${personalInfo.contact.github}`}
            target="_blank"
            rel="noopener noreferrer"
          >
            GitHub
          </a>
          <a href={`mailto:${personalInfo.contact.email}`}>Email</a>
        </div>
      </div>
    </motion.footer>
  );
};

export default Footer;
