import { motion } from 'framer-motion';
import './Button.css';

interface ButtonProps {
  children: React.ReactNode;
  variant?: 'primary' | 'secondary';
  href?: string;
  onClick?: () => void;
  className?: string;
}

const Button = ({ children, variant = 'primary', href, onClick, className = '' }: ButtonProps) => {
  const buttonClass = `btn btn-${variant} ${className}`;

  const motionProps = {
    whileHover: { y: -2, scale: 1.02 },
    whileTap: { scale: 0.98 },
    transition: { type: 'spring' as const, stiffness: 400, damping: 17 },
  };

  if (href) {
    return (
      <motion.a href={href} className={buttonClass} {...motionProps}>
        <span className="btn-content">{children}</span>
        {variant === 'primary' && <span className="btn-shimmer" />}
      </motion.a>
    );
  }

  return (
    <motion.button onClick={onClick} className={buttonClass} {...motionProps}>
      <span className="btn-content">{children}</span>
      {variant === 'primary' && <span className="btn-shimmer" />}
    </motion.button>
  );
};

export default Button;
