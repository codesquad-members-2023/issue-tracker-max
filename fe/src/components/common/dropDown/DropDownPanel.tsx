import { Theme, css } from '@emotion/react';
import { DropDownHeader } from './DropDownHeader';
import { useEffect } from 'react';

type Props = {
  position: keyof typeof POSITION;
  panelHeader: string;
  children: React.ReactNode;
  onOutsideClick: () => void;
};

export const DropDownPanel: React.FC<Props> = ({
  position,
  panelHeader,
  children,
  onOutsideClick,
}) => {
  useEffect(() => {
    const onClick = ({ target }: MouseEvent) => {
      if (target instanceof Element && target?.closest('.dropdown-panel')) {
        return;
      }

      onOutsideClick();
    };

    window.addEventListener('click', onClick);

    return () => {
      window.removeEventListener('click', onClick);
    };
  }, [onOutsideClick]);

  return (
    <div css={(theme) => panelStyle(theme, position)}>
      <DropDownHeader panelHeader={panelHeader} />
      <ul className="list-container">{children}</ul>
    </div>
  );
};

const panelStyle = (theme: Theme, position: keyof typeof POSITION) => css`
  position: absolute;
  top: 100%;
  z-index: 50;
  width: 240px;
  border-radius: ${theme.radius.l};
  border: ${`${theme.border.default} ${theme.neutral.border.default}`};
  background: ${theme.neutral.surface.default};
  overflow: hidden;
  ${POSITION[position]};

  .list-container {
    max-height: 288px;
    overflow-y: auto;

    li {
      border-bottom: ${`${theme.border.default} ${theme.neutral.border.default}`};
    }
  }
`;

const POSITION = {
  left: {
    left: 0,
  },

  right: {
    right: 0,
  },

  center: {
    left: 'calc(-120px + 50%)',
  },
};
