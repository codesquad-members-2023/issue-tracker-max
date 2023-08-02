import { css, useTheme } from '@emotion/react';
import { InformationTag } from '../InformationTag';

type SelectedLabelsData = {
  labelId: number;
  name: string;
  backgroundColor: string;
  textColor: string;
};

type Props = {
  selectedLabelsData: SelectedLabelsData[];
};

export const ListLabel: React.FC<Props> = ({ selectedLabelsData }) => {
  return (
    <div
      css={{
        display: 'flex',
        gap: '4px',
        flexWrap: 'wrap',
        marginTop: '-16px',
      }}
    >
      {selectedLabelsData.map((label) => (
        <InformationTag
          size="S"
          fillColor={label.backgroundColor}
          textColor={label.textColor}
          typeVariant="filled"
        >
          {label.name}
        </InformationTag>
      ))}
    </div>
  );
};
