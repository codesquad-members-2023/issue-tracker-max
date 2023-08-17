import { useEffect, useState } from 'react';
import { styled } from 'styled-components';
import DropdownIndicator from '../common/DropdownIndicator';
import useAxiosPrivate from '../../hooks/useAxiosPrivate';
import DropdownPanelElement from '../../types/DropdownPanelElement';
import Option from '../../constant/Option';
import { Label } from '../../types';
import DropdownType from '../../constant/DropdownType';

export default function LabelIndicator() {
  const [labels, setLabels] = useState<Label[]>([]);
  const axiosPrivate = useAxiosPrivate();

  useEffect(() => {
    async function fetchData() {
      const res = await axiosPrivate.get('api/labels');
      const { labels } = res.data.message;
      try {
        setLabels(labels);
      } catch (error) {
        console.error(error);
      }
    }

    fetchData();
  }, [axiosPrivate]);

  return (
    <Container>
      <h3 className="blind">레이블</h3>
      <DropdownIndicator
        type={DropdownType.label}
        text="레이블"
        label="레이블 필터"
        elements={labelsToElements(labels)}
      />
    </Container>
  );
}

const Container = styled.section``;

function labelsToElements(labels: Label[]) {
  const elements: DropdownPanelElement<DropdownType.label>[] = [];
  labels.forEach(({ name, textColor, backgroundColor, description }) =>
    elements.push({
      type: DropdownType.label,
      text: description,
      option: Option.Available,
      textColor,
      backgroundColor,
      name,
    })
  );

  return elements;
}
