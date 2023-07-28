import { styled } from 'styled-components';
import Icons from '../design/Icons';

type ButtonProps = {
  iconName?: keyof typeof Icons;
  text: string;
} & StyledProps;

type StyledProps = {
  $Flexible: 'Flexible' | 'Fixed';
  $Type: 'Contained' | 'Outline' | 'Ghost';
  $ElementPattern: 'TextOnly' | 'Icon+Text';
  $State: 'Enabled' | 'Hover' | 'Press' | 'Disabled' | 'Selected/Active';
  $Size: 'L' | 'M' | 'S';
};

export default function Button({
  $Flexible,
  $Type,
  $ElementPattern,
  $State = 'Enabled',
  $Size,
  iconName,
  text,
}: ButtonProps) {
  const Icon = iconName && toStyledIcon(iconName);

  return (
    <Container
      $Flexible={$Flexible}
      $Type={$Type}
      $ElementPattern={$ElementPattern}
      $State={$State}
      $Size={$Size}>
      {Icon && $ElementPattern === 'Icon+Text' && (
        <Icon {...{ $Type, $Size, $State }} />
      )}
      <TextLabel {...{ $Type, $State, $Size }}>{text}</TextLabel>
    </Container>
  );
}

const Container = styled.button<StyledProps>`
  cursor: pointer;
  box-sizing: border-box;
  display: inline-flex;
  align-items: center;
  justify-content: center;

  ${({ $Flexible }) =>
    $Flexible === 'Flexible' ? '' : 'width: auto !important;'};

  ${({ theme, $Type }) => {
    const { color } = theme;
    switch ($Type) {
      case 'Contained':
        return `
          color: ${color.brand.text.default};
          background-color: ${color.brand.surface.default};
          border: none;
        `;
      case 'Outline':
        return `
          color: ${color.brand.text.weak};
          border: 1px solid ${color.brand.surface.default};
          background-color: transparent;
        `;
      case 'Ghost':
        return `
          color: ${color.neutral.text.default};
          border: none;
          background-color: transparent;
        `;
    }
  }}

  opacity: ${({ theme, $State }) => {
    const { objectStyles } = theme;
    switch ($State) {
      case 'Selected/Active':
      case 'Enabled':
        return 1;
      case 'Hover':
        return objectStyles.opacity.hover;
      case 'Press':
        return objectStyles.opacity.press;
      case 'Disabled':
        return objectStyles.opacity.disabled;
    }
  }};

  ${({ theme, $Type, $Size }) => {
    if ($Type === 'Ghost') {
      return ``;
    }
    const { objectStyles } = theme;
    switch ($Size) {
      case 'L':
        return `
          min-width: 240px;
          height: 56px;
          padding: 0 24px;
          border-radius: ${objectStyles.radius.large};
        `;
      case 'M':
        return `
          min-width: 184px;
          height: 48px;
          padding: 0 24px;
          border-radius: ${objectStyles.radius.medium};
        `;
      case 'S':
        return `
          min-width: 128px
          height: 40px;
          padding: 0 16px;
          border-radius: ${objectStyles.radius.medium};
        `;
    }
  }}
`;

const TextLabel = styled.span<{
  $Size: 'L' | 'M' | 'S';
  $Type: 'Contained' | 'Outline' | 'Ghost';
  $State: 'Enabled' | 'Hover' | 'Press' | 'Disabled' | 'Selected/Active';
}>`
  ${({ theme, $Size }) => {
    const { font } = theme;
    switch ($Size) {
      case 'L':
        return `
          ${font.available.medium[20]}
          padding: 0 8px;
        `;
      case 'M':
        return `
          ${font.available.medium[16]}
          padding: 0 8px;
        `;
      case 'S':
        return `
          ${font.available.medium[12]}
          padding: 0 4px;
        `;
    }
  }}

  ${({ theme, $Type, $State, $Size }) => {
    const { font, color } = theme;
    const fontStyle = `color: ${color.neutral.text.strong};`;

    if ($Type !== 'Ghost' || $State !== 'Selected/Active') {
      return '';
    }
    switch ($Size) {
      case 'L':
        return fontStyle + `${font.selected.bold[20]}`;
      case 'M':
        return fontStyle + `${font.selected.bold[16]}`;
      case 'S':
        return fontStyle + `${font.selected.bold[12]}`;
    }
  }}
`;

function toStyledIcon(iconName: keyof typeof Icons) {
  return styled(Icons[iconName])<{
    $Type: 'Contained' | 'Outline' | 'Ghost';
    $Size: 'L' | 'M' | 'S';
    $State: 'Enabled' | 'Hover' | 'Press' | 'Disabled' | 'Selected/Active';
  }>`
    width: ${({ $Size }) => ($Size === 'L' ? '24px' : '16px')};
    height: ${({ $Size }) => ($Size === 'L' ? '24px' : '16px')};

    ${({ theme, $Type, $State }) => {
      const { color } = theme;
      switch ($Type) {
        case 'Contained':
          return `
            stroke: ${color.brand.text.default};
            fill: ${color.brand.text.default};
          `;
        case 'Outline':
          return `
            stroke: ${color.brand.text.weak};
            fill: ${color.brand.text.weak};
          `;
        case 'Ghost':
          if ($State === 'Selected/Active') {
            return `
              stroke: ${color.neutral.text.strong};
              fill: ${color.neutral.text.strong};
            `;
          }
          return `
            stroke: ${color.neutral.text.default};
            fill: ${color.neutral.text.default};
          `;
      }
    }};
  `;
}
