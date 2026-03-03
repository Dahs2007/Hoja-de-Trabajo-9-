import { motion } from 'framer-motion';
import { Mail, GithubIcon, LinkedinIcon } from 'lucide-react';
import { Button } from '../ui';
import { personalInfo } from '../../data/personal';
import './Contact.css';

const contactMethods = [
  {
    icon: <Mail size={28} strokeWidth={1.5} />,
    label: 'Email',
    value: personalInfo.contact.email,
    href: `mailto:${personalInfo.contact.email}`,
  },
  {
    icon: <GithubIcon size={28} strokeWidth={1.5} />,
    label: 'GitHub',
    value: `github.com/${personalInfo.contact.github}`,
    href: `https://github.com/${personalInfo.contact.github}`,
  },
  {
    icon: <LinkedinIcon size={28} strokeWidth={1.5} />,
    label: 'LinkedIn',
    value: `Iván Roblero`,
    href: `https://www.linkedin.com/in/${personalInfo.contact.linkedin}`,
  },
];

const Contact = () => {
  return (
    <section id="contacto" className="contact">
      <motion.div
        className="contact-container"
        initial={{ opacity: 0, y: 30 }}
        whileInView={{ opacity: 1, y: 0 }}
        viewport={{ once: true, margin: '-100px' }}
        transition={{ duration: 0.6 }}
      >
        <h2 className="contact-title">Trabajemos Juntos</h2>
        <p className="contact-subtitle">
          ¿Tienes un proyecto en mente? ¿Buscas un desarrollador comprometido y apasionado?
          Estoy disponible para pasantías, empleos y proyectos freelance.
        </p>

        <Button href={`mailto:${personalInfo.contact.email}`} variant="primary">
          Envíame un mensaje →
        </Button>

        <div className="contact-methods">
          {contactMethods.map((method, index) => (
            <motion.a
              key={method.label}
              href={method.href}
              className="contact-card"
              target={method.label !== 'Email' ? '_blank' : undefined}
              rel={method.label !== 'Email' ? 'noopener noreferrer' : undefined}
              initial={{ opacity: 0, y: 20 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true }}
              transition={{ delay: index * 0.1, duration: 0.5 }}
              whileHover={{ y: -10, boxShadow: '0 8px 25px rgba(0, 0, 0, 0.08)' }}
            >
              <span className="contact-icon">{method.icon}</span>
              <span className="contact-label">{method.label}</span>
              <span className="contact-value">{method.value}</span>
            </motion.a>
          ))}
        </div>
      </motion.div>
    </section>
  );
};

export default Contact;
