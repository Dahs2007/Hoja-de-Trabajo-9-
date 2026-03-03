export interface Project {
  id: number;
  title: string;
  description: string;
  tags: string[];
  image: string;
  liveUrl?: string;
  githubUrl?: string;
  date: string;
  type: 'university' | 'internship' | 'personal';
}

export interface Skill {
  icon: string;
  title: string;
  items: string[];
}

export interface Education {
  icon: string;
  institution: string;
  degree: string;
  status: 'in-progress' | 'completed';
  period: string;
  highlights: string[];
}

export interface Achievement {
  icon: string;
  title: string;
  institution: string;
  category: string;
  description: string;
  date: string;
}

export interface PersonalInfo {
  name: string;
  logo: string;
  location: string;
  locationFlag: string;
  title: string;
  subtitle: string;
  about: {
    intro: string;
    passion: string;
    style: string;
    hobbies: string[];
  };
  stats: {
    yearsExperience: number;
    projectsCompleted: number;
    technologies: number;
    englishLevel: string;
  };
  contact: {
    email: string;
    github: string;
    linkedin?: string;
  };
}

export interface SoftSkill {
  name: string;
  icon: string;
}
