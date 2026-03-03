import { SectionHeader, SkillCard } from '../ui';
import { technicalSkills, softSkillsData } from '../../data/skills';
import './Skills.css';

const Skills = () => {
  const allSkills = [...technicalSkills, softSkillsData];

  return (
    <section id="habilidades" className="skills">
      <SectionHeader
        tag="// Stack Tecnológico"
        title="Tecnologías & Herramientas"
        description="Herramientas que domino para crear soluciones robustas y escalables"
      />

      <div className="skills-grid">
        {allSkills.map((skill, index) => (
          <SkillCard key={skill.title} skill={skill} index={index} />
        ))}
      </div>
    </section>
  );
};

export default Skills;
